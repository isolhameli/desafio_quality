package com.mercadolibre.desafio_quality.unit.services;

import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.models.Room;
import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.requests.RoomRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;
import com.mercadolibre.desafio_quality.services.DistrictServiceImpl;
import com.mercadolibre.desafio_quality.services.PropertyServiceImpl;
import com.mercadolibre.desafio_quality.services.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @Mock
    private DistrictServiceImpl districtService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    private static List<Room> rooms;
    private static PropertyRequest propertyRequest;
    private static District district;
    private static Room largestRoom;

    @BeforeAll
    static void init(){
        List<RoomRequest> roomRequests = new ArrayList<RoomRequest>(List.of(
                new RoomRequest("Sala",5.0,4.0),
                new RoomRequest("Cozinha",3.0,4.5)));

        rooms = new ArrayList<Room>(List.of(
                new Room("Sala",5.0,4.0,20.0),
                new Room("Cozinha",3.0,4.5,13.5)));

        propertyRequest = new PropertyRequest("Casa","Bairro dos Estados",roomRequests);
        district = new District("BAIRRO DOS ESTADOS",450.0);
        largestRoom = rooms.get(0);


    }

    @Test
    void testCalculatePriceGivesRightResult(){
        //given
        Double area = 10.5;
        Double price = 580.0;
        Double expectedPrice = 6090.0;

        //when
        Double totalPrice = propertyService.calculatePropertyPrice(area,price);

        //assert
        Assertions.assertEquals(expectedPrice, totalPrice);
    }

    @Test
    void testGetPropertyInfo(){
        //given
        Double totalArea = 33.5;
        Double prorpertyPrice = 15075.0;
        PropertyResponse expectedResponse = new PropertyResponse(propertyRequest, district, totalArea,
                prorpertyPrice, largestRoom, rooms);

        //when
        when(roomService.getRoomsWithArea(propertyRequest.getRooms())).thenReturn(rooms);
        when(roomService.calculateTotalArea(rooms)).thenReturn(totalArea);
        when(districtService.findByName(propertyRequest.getPropertyDistrict())).thenReturn(district);
        when(roomService.getLargestRoom(rooms)).thenReturn(largestRoom);

        //then
        Assertions.assertEquals(expectedResponse,propertyService.getPropertyInfo(propertyRequest));

    }
}
