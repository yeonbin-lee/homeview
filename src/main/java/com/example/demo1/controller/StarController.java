package com.example.demo1.controller;

import com.example.demo1.dto.RoomDTO;
import com.example.demo1.dto.StarDTO;
import com.example.demo1.entity.Star;
import com.example.demo1.service.StarService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/star")
@AllArgsConstructor
public class StarController {

    private final StarService starService;

    // 존재하면 저장 후 true, 존재하지않으면 에러메시지 반환
    @PostMapping("/add")
    public ResponseEntity addStar(@RequestBody StarDTO starDTO, HttpSession httpSession) {
        String email = (String) httpSession.getAttribute("email");
        if(email.equals(starDTO.getMember().getEmail())){
            try{
                return ResponseEntity.ok(starService.starRoom(starDTO));
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }else{
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 존재하면 삭제 후 true, 존재하지않으면 에러메시지 반환
    @DeleteMapping("/delete")
    public ResponseEntity deleteStar(@RequestBody StarDTO starDTO, HttpSession httpSession){
        String email = (String) httpSession.getAttribute("email");
        if(email.equals(starDTO.getMember().getEmail())) {
            try {
                return ResponseEntity.ok(starService.deleteStar(starDTO));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*존재하면 true, 존재하지않으면 false*/
    @GetMapping("/check")
    public ResponseEntity checkStar(@RequestBody StarDTO starDTO, HttpSession httpSession){
        String email = (String) httpSession.getAttribute("email");
        if(email.equals(starDTO.getMember().getEmail())){
            return ResponseEntity.ok(starService.checkStar(starDTO));
        }else{
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity addStar(@RequestBody StarDTO starDTO) {
//        try{
//            return ResponseEntity.ok(starService.starRoom(starDTO));
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }

//    // 존재하면 삭제 후 true, 존재하지않으면 에러메시지 반환
//    @DeleteMapping("/delete")
//    public ResponseEntity deleteStar(@RequestBody StarDTO starDTO){
//
//        try{
//            return ResponseEntity.ok(starService.deleteStar(starDTO));
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }

//
//    @GetMapping("/check")
//    public ResponseEntity checkStar(@RequestBody StarDTO starDTO){
//        return ResponseEntity.ok(starService.checkStar(starDTO));
//    }
}
