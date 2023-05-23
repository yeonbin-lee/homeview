package com.example.demo1.repository;

import com.example.demo1.entity.Member;
import com.example.demo1.entity.Role;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void save() {
        //given
        Member member = Member.builder().nickname("hiho").name("bibi").email("12344@naaverc").password("1234@@").build();

        //when
        Member saveMember = memberRepository.save(member);

        System.out.println("이름은?" + saveMember.getName());
        System.out.println("비밀번호는?" + saveMember.getPassword());
        //then
        Assertions.assertThat(member.getName()).isEqualTo(saveMember.getName());
    }
}