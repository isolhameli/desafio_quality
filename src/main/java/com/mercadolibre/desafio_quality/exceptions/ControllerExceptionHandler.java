package com.mercadolibre.desafio_quality.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DistrictNotFoundException.class)
    public ResponseEntity<StandardError> districtNotFoundError(DistrictNotFoundException e){
        StandardError standardError = new StandardError(e.getMessage(),
                HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e) throws ClassNotFoundException {
        ValidationError validationError = new ValidationError("Erro de validação", HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(), new ArrayList());
        Map<String,FieldMessage> errors = new HashMap<>();
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
        validationError.setErrors(errors.values().stream().collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }

    private String findJsonProperty(FieldError fieldError, MethodArgumentNotValidException e) throws ClassNotFoundException {
        Class errorClass = e.getBindingResult().getTarget().getClass();
        String fieldOriginalName = fieldError.getField();
        String[] splitString = fieldOriginalName.split("\\.");
        return findSerializedPath(splitString, errorClass);
    }

    private String findSerializedPath(String[] splitString, Class errorClass) throws ClassNotFoundException {
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
                errorClass = propertyField.getType();
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
