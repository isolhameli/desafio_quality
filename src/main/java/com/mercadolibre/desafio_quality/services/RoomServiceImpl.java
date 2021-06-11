package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.interfaces.RoomInterface;
import com.mercadolibre.desafio_quality.models.Room;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService{
    @Override
    public <T extends RoomInterface> List<Room> getRoomsWithArea(List<T> rooms) {
        return rooms
                .stream()
                .map(room -> new Room(room.getName(),room.getLength(),
                        room.getWidth(), calculateRoomArea(room)))
                .collect(Collectors.toList());
    }

    @Override
    public <T extends RoomInterface> Double calculateRoomArea(T room) {
        return room.getWidth()*room.getLength();
    }

    @Override
    public Room getLargestRoom(List<Room> rooms) {
        return Collections.max(rooms, Comparator.comparing(Room::getArea));
    }

    @Override
    public Double calculateTotalArea(List<Room> rooms) {
        return rooms.stream().mapToDouble(Room::getArea).sum();
    }
}
