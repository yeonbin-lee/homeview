package com.example.demo1.repository;

import com.example.demo1.entity.Room;
import com.example.demo1.entity.S3;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repository extends JpaRepository<S3, Long> {
}
