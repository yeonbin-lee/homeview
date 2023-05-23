package com.example.demo1.service;

import com.example.demo1.dto.LoginDTO;
import com.example.demo1.dto.SignupDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member joinUser(SignupDTO signupDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        signupDTO.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        return memberRepository.save(signupDTO.toEntity());
    }

    public Member loginUser(LoginDTO loginDTO){
        Optional<Member> findMember = memberRepository.findByEmail(loginDTO.getEmail());
        return findMember.get();
    }

    //checkEmail --> 중복체크
}
