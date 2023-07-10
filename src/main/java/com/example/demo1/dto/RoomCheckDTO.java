package com.example.demo1.dto;

import com.example.demo1.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomCheckDTO {

    private String old_address;
    private String building;

}
