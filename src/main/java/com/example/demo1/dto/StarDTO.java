package com.example.demo1.dto;

import com.example.demo1.entity.Member;
import com.example.demo1.entity.Room;
import com.example.demo1.entity.Star;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StarDTO {

    private Member member;
    private Room room;

    public Star toEntity(){
        return Star.builder()
                .member(member)
                .room(room)
                .build();
    }

}
