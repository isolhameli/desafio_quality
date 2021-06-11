package com.mercadolibre.desafio_quality.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.models.Room;
import com.mercadolibre.desafio_quality.requests.PropertyRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonPropertyOrder({"prop_name","prop_district","total_area_m2","prop_value","largest_room","rooms"})
public class PropertyResponse {

    @JsonProperty("prop_name")
    private String propertyName;

    @JsonProperty("prop_district")
    private String propertyDistrict;

    @JsonProperty("total_area_m2")
    private Double totalArea;

    @JsonProperty("prop_value")
    private Double propertyValue;

    @JsonProperty("largest_room")
    private RoomResponse largestRoom;

    private List<RoomResponse> rooms;

    @Deprecated
    public PropertyResponse() {
    }

    public PropertyResponse(PropertyRequest propertyRequest, District district, Double totalArea, Double propertyValue, Room largestRoom, List<Room> rooms) {
        this.propertyName = propertyRequest.getPropertyName();
        this.propertyDistrict = district.getName();
        this.totalArea = totalArea;
        this.propertyValue = propertyValue;
        this.largestRoom = new RoomResponse(largestRoom);
        this.rooms = rooms.stream().map(RoomResponse::new).collect(Collectors.toList());
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

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(Double propertyValue) {
        this.propertyValue = propertyValue;
    }

    public RoomResponse getLargestRoom() {
        return largestRoom;
    }

    public void setLargestRoom(RoomResponse largestRoom) {
        this.largestRoom = largestRoom;
    }

    public List<RoomResponse> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomResponse> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyResponse)) return false;
        PropertyResponse that = (PropertyResponse) o;
        return propertyName.equals(that.propertyName) && propertyDistrict.equals(that.propertyDistrict) && totalArea.equals(that.totalArea) && propertyValue.equals(that.propertyValue) && largestRoom.equals(that.largestRoom) && rooms.equals(that.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName, propertyDistrict, totalArea, propertyValue, largestRoom, rooms);
    }
}
