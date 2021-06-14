package com.mercadolibre.desafio_quality.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mercadolibre.desafio_quality.exceptions.utils.FieldMessage;
import com.mercadolibre.desafio_quality.exceptions.utils.ParamRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<StandardError> invalidBody(JsonParseException e){
        StandardError standardError = new StandardError("Corpo inválido", HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }

    @ExceptionHandler(DistrictNotFoundException.class)
    public ResponseEntity<StandardError> districtNotFoundError(DistrictNotFoundException e){
        StandardError standardError = new StandardError(e.getMessage(),
                HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    private ResponseEntity<ParseNestedObjectException> invalidEntity(MismatchedInputException e) throws ClassNotFoundException {

        Field[] fields;
        Class errorClass;
        Class outerErrorClass = e.getPath().get(0).getFrom().getClass();
        if (e.getMessage().contains("ArrayList<")){
            errorClass = Class.forName(e.getMessage().split("ArrayList<")[1].split(">")[0]);
            fields = errorClass.getDeclaredFields();
        } else{
            errorClass = Class.forName(e.getTargetType().getName());
            fields = errorClass.getDeclaredFields();
        }
        String errorField = getMismatchedInputExceptionPropertyPath(e.getPath());
        ParseNestedObjectException err = populateParamRequirements(errorField, fields, outerErrorClass);
        err.setMessage("Valor inválido para o campo "+errorField);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private ParseNestedObjectException populateParamRequirements(String errorField, Field[] fields, Class errorClass) throws ClassNotFoundException {
        ParseNestedObjectException err = new ParseNestedObjectException(HttpStatus.BAD_REQUEST.value(),"Corpo inválido", new ArrayList(),
                System.currentTimeMillis());

        List<ParamRequirements> paramRequirements = new ArrayList<>();
        outerloop:
        for (Field field: fields){
            for (Annotation ann: field.getDeclaredAnnotations()){
                Class annotationType = ann.annotationType();
                if (annotationType.equals(NotBlank.class) ||
                        annotationType.equals(NotNull.class)){
                    paramRequirements.add(new ParamRequirements(
                            findSerializedPath(errorField+"."+field.getName(), errorClass)
                            ,field.getType().getSimpleName(), true));
                    continue outerloop;
                }
            }
            paramRequirements.add(new ParamRequirements(errorField+"."+field.getName()
                    ,field.getType().getSimpleName(), false));
        };
        err.setParams(paramRequirements);
        return err;
    }

    @ExceptionHandler(MismatchedInputException.class)
    private ResponseEntity invalidField(MismatchedInputException e) throws ClassNotFoundException {
        if (e.getMessage().contains("Cannot deserialize value of type")
                || e.getMessage().contains("Cannot construct instance of")){
            return invalidEntity(e);
        }
        String invalidProperty = getMismatchedInputExceptionPropertyPath(e.getPath());
        String valueProvided = ((InvalidFormatException) e).getValue().toString();
        String targetType = ((InvalidFormatException) e).getTargetType().getSimpleName();
        String errorMessage = "Valor de " + invalidProperty + ": " + valueProvided + " precisa ser do tipo " + targetType;
        StandardError err = new StandardError(errorMessage,HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private String getMismatchedInputExceptionPropertyPath(List<JsonMappingException.Reference> references){
        String result = "";

        for (JsonMappingException.Reference reference: references){
            if (reference.getFieldName() == null){
                result = result.substring(0, result.length()-1)+"["+reference.getIndex()+"]";
            } else{
                result += reference.getFieldName();
            }
            result+=".";
        }
        return result.substring(0, result.length()-1);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e) throws ClassNotFoundException {
        ValidationError validationError = new ValidationError("Erro de validação", HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(), new ArrayList());
        Map<String, FieldMessage> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()){
            String fieldName = findJsonProperty(fieldError, e);
            if (errors.containsKey(fieldName)){
                errors.get(fieldName).getErrors().add(fieldError.getDefaultMessage());
            } else {
                FieldMessage fieldMessage = new FieldMessage(fieldName,
                        new ArrayList(List.of(fieldError.getDefaultMessage())));
                errors.put(fieldName,fieldMessage);
            }
        }
        List<FieldMessage> errorList = errors.values().stream().collect(Collectors.toList());
        Collections.sort(errorList, Comparator.comparing((FieldMessage el) -> el.getFieldName().split(".").length).thenComparing(
                el -> el.getFieldName()
                ));
        validationError.setErrors(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }

    private String findJsonProperty(FieldError fieldError, MethodArgumentNotValidException e) throws ClassNotFoundException {
        Class errorClass = e.getBindingResult().getTarget().getClass();
        String fieldOriginalName = fieldError.getField();
        return findSerializedPath(fieldOriginalName, errorClass);
    }

    private String findSerializedPath(String fieldOriginalName, Class errorClass) throws ClassNotFoundException {
        String[] splitString = fieldOriginalName.split("\\.");
        String finalString = "";
        for (String property : splitString){
            String[] splitProperty = property.split("\\[");
            Field propertyField = findFieldFromNameAndClass(splitProperty[0], errorClass);
            finalString+=findJsonPropertyFromField(propertyField, errorClass);
            if (property.contains("[")){
                finalString+="["+splitProperty[1];
                property = splitProperty[0];
                errorClass = findListObjectClassFromString(property, errorClass);
            }
            else{
                if (propertyField.getType() == List.class){
                    finalString+="[0]";
                    errorClass = findListObjectClassFromString(property,errorClass);
                } else{
                    errorClass = propertyField.getType();
                }
            }
            finalString+=".";
        }
        return finalString.substring(0,finalString.length()-1);
    }

    private Field findFieldFromNameAndClass(String property, Class errorClass){
        return Arrays.stream(errorClass.getDeclaredFields())
                .filter(item -> item.getName().equals(property))
                .findFirst()
                .get();
    }

    private Class findListObjectClassFromString(String property, Class errorClass) throws ClassNotFoundException {
        String finalProperty = property;
        String errorClassString = Arrays.stream(errorClass.getDeclaredFields())
                .filter(item -> item.getName().equals(finalProperty))
                .findFirst()
                .get()
                .toGenericString()
                .split("List<")[1]
                .split(">")[0];
        return Class.forName(errorClassString);
    }



    private String findJsonPropertyFromField(Field field, Class errorClass){
        try{
            return field.getDeclaredAnnotation(JsonProperty.class).value();
        } catch (Exception exception){
            }

        for (Method method : errorClass.getDeclaredMethods()){
            String propertySetter = "set"+field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            if (method.getName().equals(propertySetter)){
                try{
                    return method.getDeclaredAnnotation(JsonProperty.class).value();
                } catch (Exception e){ }
                try {
                    return method.getDeclaredAnnotation(JsonSetter.class).value();
                } catch (Exception e){}
                return field.getName();
            }

        }
        return field.getName();
    }
}
