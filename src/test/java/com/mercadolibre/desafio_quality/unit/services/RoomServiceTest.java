package com.mercadolibre.desafio_quality.unit.services;

import com.mercadolibre.desafio_quality.models.Room;
import com.mercadolibre.desafio_quality.requests.RoomRequest;
import com.mercadolibre.desafio_quality.services.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    private final RoomServiceImpl roomService = new RoomServiceImpl();


    private static Room aRoom;
    private static RoomRequest aRoomRequest;
    private static List<RoomRequest> roomRequests;
    private static List<Room> rooms;
    private static Room largestRoom;

    @BeforeAll
    static void init(){
        aRoom = new Room("Teste",10.0,20.0,200.0);
        aRoomRequest = new RoomRequest("Teste",10.0,20.0);
        roomRequests = new ArrayList<>(List.of(
                new  RoomRequest("Quarto", 8.0, 3.5),
                new  RoomRequest("Sala", 10.0, 10.0),
                new  RoomRequest("Cozinha", 5.0, 4.0)
        ));

        rooms = new ArrayList<>(List.of(
                new  Room("Quarto", 8.0, 3.5, 28.0),
                new  Room("Sala", 10.0, 10.0,100.0),
                new  Room("Cozinha", 5.0, 4.0,20.0)
        ));

        largestRoom = rooms.get(1);
    }

    @Test
    void testCalculateRoomAreaWorksWithRoom(){
        //given
        Double expected = 200.0;

        //when
        Double area = roomService.calculateRoomArea(aRoom);

        //then
        Assertions.assertEquals(expected,area);
    }

    @Test
    void testCalculateRoomAreaWorksWithRoomRequest(){
        //given
        Double expected = 200.0;

        //when
        Double area = roomService.calculateRoomArea(aRoomRequest);

        //then
        Assertions.assertEquals(expected,area);
    }

    @Test
    void testGetRoomsWithAreaWorksWithRoomRequest(){
        //when
        List<Room> roomsResponse = roomService.getRoomsWithArea(roomRequests);

        //then
        Assertions.assertTrue(roomsResponse.size() == roomRequests.size());
        for (int i=0; i< roomRequests.size(); i++){
            Assertions.assertEquals(rooms.get(i),roomsResponse.get(i));
        }

    }

    @Test
    void testGetRoomsWithAreaWorksWithRooms(){
        //when
        List<Room> roomsResponse = roomService.getRoomsWithArea(rooms);

        //then
        Assertions.assertTrue(roomsResponse.size() == rooms.size());
        for (int i=0; i< roomRequests.size(); i++){
            Assertions.assertEquals(rooms.get(i),roomsResponse.get(i));
        }

    }

    @Test
    void testGetLargestRoom(){
        //when
        Room roomResponse = roomService.getLargestRoom(rooms);

        //then
        Assertions.assertEquals(largestRoom, roomResponse);

    }

    @Test
    void testCalculateTotalArea(){

        //given
        Double expectedArea = 148.0;

        //when
        Double actualArea = roomService.calculateTotalArea(rooms);

        //then
        Assertions.assertEquals(expectedArea, actualArea);
    }

}
