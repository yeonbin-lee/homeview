package com.example.demo1.repository;

import com.example.demo1.entity.Review;
import com.example.demo1.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findListByRoom(Room room);
}