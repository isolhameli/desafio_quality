package com.mercadolibre.desafio_quality.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolibre.desafio_quality.models.Room;

import java.util.Objects;

public class RoomResponse {

    @JsonProperty("room_name")
    private String name;
    @JsonProperty("area_m2")
    private Double area;


    public RoomResponse() {
    }

    public RoomResponse(Room room){
        this.name = room.getName();
        this.area = room.getArea();
    }

    public String getName() {
        return name;
    }

    public Double getArea() {
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomResponse)) return false;
        RoomResponse that = (RoomResponse) o;
        return name.equals(that.name) && area.equals(that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }
}
