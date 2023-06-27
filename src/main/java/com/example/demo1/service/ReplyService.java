package com.example.demo1.service;

import com.example.demo1.dto.ReplyDTO;
import com.example.demo1.dto.ReplyUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Reply;
import com.example.demo1.repository.ReplyRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private MemberService memberService;

    private Member getMember(HttpSession session) {

        //세션 객체 안에 있는 email정보 저장
        String email = (String) session.getAttribute("email");
        //log.info("회원정보 [session GET] email:" + email);

        // email id로 찾은 member 객체 리턴
        Member info = memberService.getInfo(email);
        return info;
    }

    @Transactional
    public Reply save(ReplyDTO replyDTO, HttpSession session) {

        Member findMember = getMember(session);
        Reply newReply = new Reply(replyDTO.getCommentId(), replyDTO.getPosting(), findMember,
                replyDTO.getContent(), replyDTO.getCommentTime());

        return replyRepository.save(newReply);
    }

    @Transactional
    public void update(Long commentId, ReplyUpdateDTO updateParam) {
        Reply reply = replyRepository.findById(commentId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 찾기 실패 : commentId를 찾을 수 없습니다.");
                });
        reply.setContent(updateParam.getContent());
    }


    // 댓글 리스트는 PostingService에 넣어두었음. PostId 사용해서


    // 댓글 상세보기
    @Transactional(readOnly = true)
    public Reply content(Long commentId) {
        return replyRepository.findById(commentId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 상세보기 실패 : commentId를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void delete(Long commentId) {
        replyRepository.deleteById(commentId);
    }

}