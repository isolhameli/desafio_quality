package com.mercadolibre.desafio_quality.exceptions.utils;

import java.util.List;

public class FieldMessage {

    public String fieldName;
    public List<String> errors;

    public FieldMessage(String fieldName, List<String> errors) {
        this.fieldName = fieldName;
        this.errors = errors;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getErrors() {
        return errors;
    }
}
