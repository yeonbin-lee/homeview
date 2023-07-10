package com.example.demo1.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.demo1.dto.StarDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Room;
import com.example.demo1.entity.Star;
import com.example.demo1.repository.MemberRepository;
import com.example.demo1.repository.RoomRepository;
import com.example.demo1.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    /*관심목록 추가*/
//    @Transactional
//    public boolean starRoom(StarDTO starDTO){
//        Member member = starDTO.getMember();
//        Room room = starDTO.getRoom();
//
//        if(isNotStar(member,room)){
//            starRepository.save(starDTO.toEntity());
//            return true;
//        }else{
//            Star star = starRepository.findByMemberAndRoom(member, room).get();
//            starRepository.delete(star);
//            return false;
//        }
//    }

    @Transactional
    public boolean starRoom(StarDTO starDTO){
        Member member = starDTO.getMember();
        Room room = starDTO.getRoom();

        if(isNotStar(member,room)){
            starRepository.save(starDTO.toEntity());
            return true;
        }else{
            throw new IllegalArgumentException("이미 처리된 요청입니다.");
            //return false;
        }
    }

    /*이미 관심목록인지 체크*/
    private boolean isNotStar(Member member, Room room){
        return starRepository.findByMemberAndRoom(member, room).isEmpty();
    }

    public boolean deleteStar(StarDTO starDTO){
        Member member = starDTO.getMember();
        Room room = starDTO.getRoom();

        if(isNotStar(member,room)){
            throw new IllegalArgumentException("이미 처리된 요청입니다.");
        }else{
            Star star = starRepository.findByMemberAndRoom(member, room).get();
            starRepository.delete(star);
            return true;
        }

    }

    /*존재하면 true, 존재하지않으면 false*/
    public boolean checkStar(StarDTO starDTO){
        Member member = starDTO.getMember();
        Room room = starDTO.getRoom();

        if(isNotStar(member,room)){
            return false;
        }else{
            return true;
        }
    }

}
