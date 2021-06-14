package com.mercadolibre.desafio_quality.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.desafio_quality.interfaces.RoomInterface;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Objects;

public class Room implements RoomInterface {

    @NotBlank(message = "O campo não pode estar vazio.")
    @Pattern(regexp = "^\\p{Lu}.*",message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Length(max = 30,message = "O comprimento do cômodo não pode exceder 30 caracteres")
    @JsonProperty("room_name")
    private String name;
    @NotNull(message = "O campo não pode estar vazio")
    @DecimalMin(value = "0.0", message = "A largura do cômodo não pode ser negativa")
    @DecimalMax(value = "25", message = "A largura máxima permitida por cômodo é de 25 metros")
    @JsonProperty("room_width")
    private Double width;
    @NotNull(message = "O campo não pode estar vazio")
    @DecimalMin(value = "0.0", message = "O comprimento do cômodo não pode ser negativo")
    @DecimalMax(value = "33", message = "O comprimento máximo permitido por cômodo é de 33 metros")
    @JsonProperty("room_length")
    private Double length;
    @NotNull(message = "O campo não pode estar vazio")
    @DecimalMin(value = "0.0", message = "A área do cômodo não pode ser negativa")
    @DecimalMax(value = "825", message = "A área máxima permitida por cômodo é de 825 metros")
    private Double area;

    @Deprecated
    public Room() {
    }

    public Room(String name, Double width, Double length, Double area) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public Double getWidth() {
        return width;
    }

    public Double getLength() {
        return length;
    }

    public Double getArea() {
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name) && Objects.equals(width, room.width) && Objects.equals(length, room.length) && Objects.equals(area, room.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, width, length, area);
    }
}
