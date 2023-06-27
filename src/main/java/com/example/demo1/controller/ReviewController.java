package com.example.demo1.controller;

import com.example.demo1.dto.ReviewDTO;
import com.example.demo1.dto.ReviewUpdateDTO;
import com.example.demo1.entity.Room;
import com.example.demo1.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewService reviewService;

    // 새로운 작성 폼 열기
    @GetMapping("/add")
    public ResponseEntity saveForm() {
        return new ResponseEntity(HttpStatus.OK);
    }

    // 리뷰 저장
    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody ReviewDTO reviewDTO, Room room, HttpSession session, BindingResult bindingResult) {

        // room 어떻게 처리할건지. 외부에서 주소 받아오도록 해야하나.. 주소 검색하는 api 사용하면 리턴값이 뭐지. kakao는 문자열.
        // 최초로 add 하면 room을 db에 등록하는 식으로?
        //roomService.save(room);을 먼저 수행하고 리뷰 등록을 수행하고 싶음..

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        reviewService.save(reviewDTO, room, session);
        return new ResponseEntity(HttpStatus.CREATED);
    }



    // 작성된 리뷰 열기 -> 리스트 형식으로 늘어놓을거면 필요없지 않나. 있어야하나..? 하나씩 열어서 나열인가?
    @GetMapping("/{reviewId}")
    public ResponseEntity findById(@PathVariable Long reviewId, Model model) {
        model.addAttribute("review", reviewService.content(reviewId));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정 폼 열기
    @GetMapping("/{reviewId}/edit")
    public ResponseEntity editForm(@PathVariable Long reviewId, Model model) {
        model.addAttribute("review", reviewService.content(reviewId));
        return new ResponseEntity(HttpStatus.OK);

    }

    // 리뷰 수정 완료
    @PostMapping("/{reviewId}/edit")
    public ResponseEntity edit(@PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateDTO review, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        reviewService.update(reviewId, review);
        // 리뷰 수정하고 나서 room 총점 업데이트 하는 것도 해야하는데
        return new ResponseEntity(HttpStatus.OK);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteById(@PathVariable Long reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}