package com.mercadolibre.desafio_quality.exceptions;

import com.mercadolibre.desafio_quality.exceptions.utils.ParamRequirements;

import java.util.List;

public class ParseNestedObjectException {

    private Integer status;
    private String message;
    private List<ParamRequirements> params;
    private Long timeStamp;

    public ParseNestedObjectException(Integer status, String message, List<ParamRequirements> params, Long timeStamp) {
        this.status = status;
        this.message = message;
        this.params = params;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ParamRequirements> getParams() {
        return params;
    }

    public void setParams(List<ParamRequirements> params) {
        this.params = params;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}