package com.example.demo1.controller;

import com.example.demo1.dto.RoomDTO;
import com.example.demo1.dto.review.ReviewDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Review;
import com.example.demo1.service.MemberService;
import com.example.demo1.service.ReviewService;
import com.example.demo1.service.S3UploadService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;


    /*session 적용한 add*/
    @PostMapping("/add")
    public ResponseEntity addReview(@Valid @RequestBody ReviewDTO reviewDTO, HttpSession httpSession, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage() , HttpStatus.BAD_REQUEST);
            }
        }

        String email = (String) httpSession.getAttribute("email");
        Member member = memberService.getInfo(email);
        reviewDTO.setMember(member);

        if(email.equals(reviewDTO.getMember().getEmail())){
            try{
                return ResponseEntity.ok(reviewService.addReview(reviewDTO));
            } catch (Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*session 적용한 delete*/
    @DeleteMapping("/delete")
    public ResponseEntity deleteReview(@RequestBody ReviewDTO reviewDTO, HttpSession httpSession){
        String email = (String) httpSession.getAttribute("email");
        if(email.equals(reviewDTO.getMember().getEmail())){
            try{
                return ResponseEntity.ok(reviewService.deleteReview(reviewDTO));
            } catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/add")
//    public ResponseEntity addReview(@Valid @RequestBody ReviewDTO reviewDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<FieldError> list = bindingResult.getFieldErrors();
//            for(FieldError error : list) {
//                return new ResponseEntity<>(error.getDefaultMessage() , HttpStatus.BAD_REQUEST);
//            }
//        }
//        try{
//            return ResponseEntity.ok(reviewService.addReview(reviewDTO));
//        } catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @DeleteMapping("/delete")
//    public ResponseEntity deleteReview(@RequestBody ReviewDTO reviewDTO){
//        try{
//            return ResponseEntity.ok(reviewService.deleteReview(reviewDTO));
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/search/{room_id}")
//    public ResponseEntity search(@PathVariable Long room_id){
//        try{
//            return ResponseEntity.ok(reviewService.search(room_id));
//        } catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
//    }

}
