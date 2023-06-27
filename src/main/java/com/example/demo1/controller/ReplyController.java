package com.example.demo1.controller;

import com.example.demo1.dto.ReplyDTO;
import com.example.demo1.dto.ReplyUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Reply;
import com.example.demo1.service.MemberService;
import com.example.demo1.service.ReplyService;
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
@RequestMapping("/api/comment")
public class ReplyController {
    // 근데 의문점. 해당 포스팅에 입력하는 댓글의 주소는 포스팅과 동일하게 해야하나
    // 일단 다르게 하겠음

    private ReplyService replyService;
    private MemberService memberService;


    private Member getMember(HttpSession session) {

        //세션 객체 안에 있는 email정보 저장
        String email = (String) session.getAttribute("email");
        //log.info("회원정보 [session GET] email:" + email);

        // email id로 찾은 member 객체 리턴
        Member info = memberService.getInfo(email);
        return info;
    }

    // 새로운 작성 폼 열기
    @GetMapping("/add")
    public ResponseEntity saveForm() {
        return new ResponseEntity(HttpStatus.OK);
    }

    // 댓글 저장
    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody ReplyDTO replyDTO, HttpSession session, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        replyService.save(replyDTO, session);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 작성된 댓글 열기
    @GetMapping("/{commentId}")
    public ResponseEntity findById(@PathVariable Long commentId, Model model) {
        model.addAttribute("comment", replyService.content(commentId));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정 폼 열기
    @GetMapping("/{commentId}/edit")
    public ResponseEntity editForm(@PathVariable Long commentId, Model model) {
        model.addAttribute("comment", replyService.content(commentId));
        return new ResponseEntity(HttpStatus.OK);

    }

    // 댓글 수정 완료
    @PostMapping("/{commentId}/edit")
    public ResponseEntity edit(@PathVariable Long commentId, @Valid @RequestBody ReplyUpdateDTO reply, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        replyService.update(commentId, reply);
        return new ResponseEntity(HttpStatus.OK);

    }

    // 포스팅 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteById(@PathVariable Long commentId) {
        replyService.delete(commentId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
