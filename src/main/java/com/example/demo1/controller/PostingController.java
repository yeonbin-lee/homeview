package com.example.demo1.controller;

import com.example.demo1.dto.PostingDTO;
import com.example.demo1.dto.PostingUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.service.MemberService;
import com.example.demo1.service.PostingService;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/posting")
@AllArgsConstructor
public class PostingController { // 스테이터스로만 보내는걸로. 문자든 숫자든
    private PostingService postingService;

    // 새로운 작성 폼 열기
    @GetMapping("/add")
    public ResponseEntity saveForm() {
        return new ResponseEntity(HttpStatus.OK);
    }

    // 포스팅 저장
    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody PostingDTO postingDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        postingService.save(postingDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 포스팅 리스트
    @GetMapping // /members?page=0&size=3&sort=id,desc&sort=username,desc  -> 요청은 이런 식으로. 여기서는 page만 따로 ? 뒤에 붙여주면 될듯. page는 0부터 시작
    public Page<Posting> index( @PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable) {

        return postingService.list(pageable); // 바디형식으로 전송해줘야할수도

        //model.addAttribute("postings", postingService.list(pageable));
    }

    // 작성된 포스팅 열기
    @GetMapping("/{postId}")
    public ResponseEntity findById(@PathVariable Long postId, Model model) {
        model.addAttribute("posting", postingService.content(postId));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정 폼 열기
    @GetMapping("/{postId}/edit")
    public ResponseEntity editForm(@PathVariable Long postId, Model model) {
        model.addAttribute("posting", postingService.content(postId));
        return new ResponseEntity(HttpStatus.OK);

    }

    // 포스팅 수정 완료
    @PostMapping("/{postId}/edit")
    public ResponseEntity edit(@PathVariable Long postId, @Valid @RequestBody PostingUpdateDTO posting, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        postingService.update(postId, posting);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 포스팅 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity deleteById(@PathVariable Long postId) {
        postingService.delete(postId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}