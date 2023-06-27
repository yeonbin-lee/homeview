package com.example.demo1.entity;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long roomId;

    @Column(nullable = false, length = 50)
    private String name;

    private int totalScore;

    @Column(nullable = false, length = 50)
    private String address;

    private Point location;
}
