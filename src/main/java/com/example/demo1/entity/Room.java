package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@NoArgsConstructor
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long roomId;

    @Column(nullable = false, length = 50)
    private String name;

    private double totalScore;

    @Column(nullable = false, length = 50)
    private String address;

    private Point location;


    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}