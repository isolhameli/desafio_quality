package com.mercadolibre.desafio_quality.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class District {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\s]{1,45}")
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private Double price;

    @Deprecated
    public District() {
    }

    public District(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof District)) return false;
        District district = (District) o;
        return Objects.equals(name, district.name) && Objects.equals(price, district.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
