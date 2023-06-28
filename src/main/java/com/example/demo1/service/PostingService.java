package com.example.demo1.service;

import com.example.demo1.dto.PostingDTO;
import com.example.demo1.dto.PostingUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.repository.PostingRepository;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private MemberService memberService;

    @Autowired
    public PostingService(PostingRepository postingRepository, MemberService memberService) {
        this.postingRepository = postingRepository;
        this.memberService = memberService;
    }

    private Member getMember(HttpSession session) {

        //세션 객체 안에 있는 email정보 저장
        String email = (String) session.getAttribute("email");
        //log.info("회원정보 [session GET] email:" + email);

        // email id로 찾은 member 객체 리턴
        Member info = memberService.getInfo(email);
        return info;
    }

    // email 지정
/*    @Transactional
    public Posting saveforTest(Posting posting, String email) {

        Member findMember = memberService.getInfo(email);
        *//*member = getMember(session);
        Long userId = member.getId();*//*
        log.info("post_id:" + posting.getPostId() + " findMember"+ findMember + " title: "+ posting.getTitle() +
                " content" + posting.getContent() + " 시간" + posting.getPostTime());
        Posting newPosting = new Posting(posting.getPostId(), findMember, posting.getTitle(), posting.getContent(),
                posting.getPostTime(), posting.getPostHits(), null);

        return postingRepository.save(newPosting);
    }*/

    @Transactional
    public Posting save(PostingDTO postingDTO) {

        log.info(postingDTO.getTitle() + "nickname은" + postingDTO.getNickname());
        Posting newPosting = new Posting(postingDTO.getPostId(), postingDTO.getMember(), postingDTO.getNickname(), postingDTO.getTitle(), postingDTO.getContent(),
                postingDTO.getPostTime(), postingDTO.getPostHits());

        return postingRepository.save(newPosting);
    }

    @Transactional
    public void update(Long postId, PostingUpdateDTO updateParam) {
        Posting posting = postingRepository.findById(postId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 찾기 실패 : postId를 찾을 수 없습니다.");
                });
        posting.setTitle(updateParam.getTitle());
        posting.setContent(updateParam.getContent());
    }

    // 글 목록
    @Transactional(readOnly = true)
    public Page<Posting> page(Pageable pageable) {

        return postingRepository.findAll(pageable);
    }


    public List<Posting> list() {

        return postingRepository.findAll();
    }

    // 댓글 목록
/*    @Transactional(readOnly = true)
    public List<Reply> replyList(Posting posting) {

        List<Reply> replies = replyRepository.findListByPosting(posting);
        if (replies == null || replies.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return replies;

    }*/


    // 글 상세보기
    @Transactional(readOnly = true)
    public Posting content(Long postId) {
        return postingRepository.findById(postId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 상세보기 실패 : postId를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void delete(Long postId) {
        postingRepository.deleteById(postId);
    }
}