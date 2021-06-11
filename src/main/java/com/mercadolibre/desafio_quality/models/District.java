package com.mercadolibre.desafio_quality.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class District {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\s]{1,45}")
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private Double price;

    public District() {
    }

    public District(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
