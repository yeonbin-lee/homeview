package com.example.demo1.controller;

import com.example.demo1.dto.LoginDTO;
import com.example.demo1.dto.SignupDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.repository.MemberRepository;
import com.example.demo1.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    // 회원가입 처리
    @GetMapping("/join")
    public String signup_page(){
        return "api/join";
    }

    @PostMapping("/join")
    public String execSignup(@RequestBody SignupDTO signupDTO){
        memberService.joinUser(signupDTO);
        return "redirect:/api/login";
    }

//     로그인 페이지
    @GetMapping("/login")
    public String login_page(){
        return "api/login";
    }

    @PostMapping("/login")
    public String execLogin(@RequestBody LoginDTO loginDTO, HttpSession session){
        Member member = memberService.loginUser(loginDTO);
        //login 성공
        if(member !=null){
            session.setAttribute("loginEmail", member.getEmail());
            return "/api/main";
        }else {
           //login 실패
           return "/api/login";
        }
    }

//    @GetMapping("/logout")
//    public String execLogout(HttpSession session){
//        session.invalidate();
//
//        return "redirect:/api/main";
//    }

}
