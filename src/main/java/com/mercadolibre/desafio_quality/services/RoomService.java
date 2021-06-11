package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.interfaces.RoomInterface;
import com.mercadolibre.desafio_quality.models.Room;

import java.util.List;

public interface RoomService {

    <T extends RoomInterface> List<Room> getRoomsWithArea(List<T> roomRequests);
    <T extends RoomInterface> Double calculateRoomArea(T room);
    Room getLargestRoom(List<Room> rooms);
    Double calculateTotalArea(List<Room> rooms);
}
