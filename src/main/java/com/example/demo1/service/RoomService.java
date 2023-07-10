package com.example.demo1.service;

import com.example.demo1.dto.RoomCheckDTO;
import com.example.demo1.dto.RoomDTO;
import com.example.demo1.dto.posting.PostingSaveDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.entity.Room;
import com.example.demo1.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private boolean isExistRoom(RoomDTO roomDTO){
        return roomRepository.findByOld_address(roomDTO.getOld_address(), roomDTO.getBuilding()).isPresent();
    }

    @Transactional
    public boolean addRoom(RoomDTO roomDTO){
        if(isExistRoom(roomDTO)){
            throw new IllegalArgumentException("이미 존재하는 방입니다.");
        }else{
            roomRepository.save(roomDTO.toEntity());
            return true;
        }
    }

    @Transactional
    public boolean deleteRoom(Long room_id){
        if(roomRepository.existsById(room_id)){
            roomRepository.deleteById(room_id);
            return true;
        }else{
            throw new IllegalArgumentException("이미 삭제된 방입니다.");
        }
    }

//    public Room check(String new_address){
//        Room room = roomRepository.findByNew_address(new_address).orElseThrow(()->
//                        new IllegalArgumentException("해당 룸은 존재하지않습니다.")
//                );
//        return room;
//    }

    public Room check(RoomCheckDTO roomCheckDTO){
        Room room = roomRepository.findByOld_address(roomCheckDTO.getOld_address(), roomCheckDTO.getBuilding()).orElseThrow(()->
                new IllegalArgumentException("해당 룸은 존재하지않습니다.")
        );
        return room;
    }

    public List<Room> getSido(String sido){
        return roomRepository.findBySido(sido);
    }




//    public Room roomInfo(Long room_id) throws Exception{
//        Room room = roomRepository.findById(room_id).orElseThrow(() ->
//                new IllegalArgumentException("해당 룸은 존재하지않습니다.")
//        );
//        return room;
//    }

    /*위는 확인된거*/




}
