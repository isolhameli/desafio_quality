package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.exceptions.DistrictNotFoundException;
import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.models.Room;
import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService{

    private final DistrictService districtService;
    private final RoomService roomService;

    public PropertyServiceImpl(DistrictService districtService, RoomService roomService) {
        this.districtService = districtService;
        this.roomService = roomService;
    }

    @Override
    public PropertyResponse getPropertyInfo(PropertyRequest propertyRequest) {

        List<Room> rooms = roomService.getRoomsWithArea(propertyRequest.getRooms());
        Double propertyArea = roomService.calculateTotalArea(rooms);
        District district = districtService.findByName(propertyRequest.getPropertyDistrict());
        Double propertyPrice = calculatePropertyPrice(propertyArea, district.getPrice());
        Room largestRoom = roomService.getLargestRoom(rooms);
        return new PropertyResponse(propertyRequest, district, propertyArea,propertyPrice,largestRoom,rooms);
    }

    @Override
    public Double calculatePropertyPrice(Double area, Double price) {
        return price*area;
    }


}
