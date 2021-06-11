package com.mercadolibre.desafio_quality.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;


public class PropertyRequest {

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Length(max = 30, message = "O comprimento do nome não pode pode exceder 30 caracteres.")
    @Pattern(regexp = "^\\p{Lu}.*",message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @JsonProperty("prop_name")
    private String propertyName;
    @NotBlank(message = "O bairro não pode estar vazio")
    @Length(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres.")
    @JsonProperty("prop_district")
    private String propertyDistrict;

    @NotEmpty(message = "O campo não pode estar vazio")
    @Valid
    private List<RoomRequest> rooms;

    @Deprecated
    public PropertyRequest() {
    }

    public PropertyRequest(String propertyName, String propertyDistrict, List<RoomRequest> rooms) {
        this.propertyName = propertyName;
        this.propertyDistrict = propertyDistrict;
        this.rooms = rooms;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDistrict() {
        return propertyDistrict;
    }

    public void setPropertyDistrict(String propertyDistrict) {
        this.propertyDistrict = propertyDistrict;
    }

    public List<RoomRequest> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomRequest> rooms) {
        this.rooms = rooms;
    }
}
