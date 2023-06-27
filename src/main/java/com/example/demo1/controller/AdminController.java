package com.example.demo1.controller;

import com.example.demo1.entity.Member;
import com.example.demo1.service.MemberService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@ResponseBody
public class AdminController {
    private MemberService memberService;

    public Member checkAdmin(HttpSession session){
        log.info("세션값 = " + session);
        String email = (String) session.getAttribute("email");
        log.info("회원정보 [session GET] email:" + email);

        // email id로 찾은 member 리턴
        Member info = memberService.getInfo(email);
        log.info("ROLE값: " + info.getRole());
        return info;
    }


    @GetMapping("/healthcheck")
    public ResponseEntity<Integer> health_check(HttpSession session){

        Member info = checkAdmin(session);
        if(info.getRole().equals("ADMIN")){
            return ResponseEntity.ok().body(200);
        }else{
            return ResponseEntity.ok().body(201); // 변경필요
        }
    }

    @GetMapping("/list")
    public List<Member> getAllMembers(HttpSession session){
        Member info = checkAdmin(session);
        if(info.getRole().equals("ADMIN")){
            return memberService.getAllMember(); // 바디형식으로 전송해줘야할수도
        }else{
            return null;// 변경필요
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, HttpSession session){
        Member info = checkAdmin(session);
        if(info.getRole().equals("ADMIN")){
            memberService.deleteById(id);
        }
    }



//    // 유저 삭제
//    @DeleteMapping("/{email}")
//    public ResponseEntity deleteUser(@PathVariable String email) {
//        Member deleteUser = memberService.deleteByEmail(email);
//
//        if(deleteUser == null) {
//            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
//        }
//        return ResponseEntity.ok(deleteUser.getEmail());
//    }

//    @DeleteMapping("/{email}")
//    public ModelAndView deleteUser(@PathVariable String email, HttpServletResponse response) throws IOException {
//        Member deleteUser = memberService.deleteByEmail(email);
//
//        if(deleteUser == null) {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "유저를 찾을 수 없습니다.");
//            return new ModelAndView();
//            // response.setStatus~
//        }
//        response.setStatus(HttpServletResponse.SC_OK);
//        return new ModelAndView();
////        return deleteUser;
//    }

    // 글 삭제
    // 리뷰 삭제

}
