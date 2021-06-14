package com.mercadolibre.desafio_quality.exceptions;

import com.mercadolibre.desafio_quality.exceptions.utils.FieldMessage;

import java.util.List;

public class ValidationError {

    private String message;
    private Integer status;
    private Long timestamp;
    private List<FieldMessage> errors;

    public ValidationError(String message, Integer status, Long timestamp, List<FieldMessage> errors) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldMessage> errors) {
        this.errors = errors;
    }
}
