package com.mercadolibre.desafio_quality.exceptions;

public class StandardError {

    private String message;
    private Integer status;
    private Long timestamp;

    public StandardError(String message, Integer status, Long timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
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
}
