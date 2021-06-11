package com.mercadolibre.desafio_quality.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.desafio_quality.interfaces.RoomInterface;
import com.mercadolibre.desafio_quality.models.Room;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class RoomRequest implements RoomInterface {

    @NotBlank(message = "O campo não pode estar vazio.")
    @Pattern(regexp = "^\\p{Lu}.*",message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Length(max = 30,message = "O comprimento do cômodo não pode exceder 30 caracteres")
    @JsonProperty("room_name")
    private String name;
    @NotNull(message = "O campo não pode estar vazio")
    @DecimalMin(value = "0.0", message = "A largura do cômodo não pode ser negativo")
    @DecimalMax(value = "25", message = "A largura máxima permitida por cômodo é de 25 metros")
    @JsonProperty("room_width")
    private Double width;
    @NotNull(message = "O campo não pode estar vazio")
    @DecimalMin(value = "0.0", message = "O comprimento do cômodo não pode ser negativo")
    @DecimalMax(value = "33", message = "O comprimento máximo permitido por cômodo é de 33 metros")
    @JsonProperty("room_length")
    private Double length;

    @Deprecated
    public RoomRequest() {
    }

    public RoomRequest(String name, Double width, Double length) {
        this.name = name;
        this.width = width;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWidth() {
        return width;
    }


    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Room toModel(Double area){
        return new Room(name, width, length, area);
    }
}
