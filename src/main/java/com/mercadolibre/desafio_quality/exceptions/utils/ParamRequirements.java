package com.mercadolibre.desafio_quality.exceptions.utils;

public class ParamRequirements {

    private String name;
    private String classType;
    private Boolean required;

    public ParamRequirements(String name, String classType, Boolean required) {
        this.name = name;
        this.classType = classType;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
