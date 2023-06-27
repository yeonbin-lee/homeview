package com.example.demo1.repository;

import com.example.demo1.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByAddress(String address);
    Room findByRoomId(Long roomId);
}