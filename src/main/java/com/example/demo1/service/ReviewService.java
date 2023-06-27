package com.example.demo1.service;

import com.example.demo1.dto.ReviewDTO;
import com.example.demo1.dto.ReviewUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Review;
import com.example.demo1.entity.Room;
import com.example.demo1.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

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
    public Review save(ReviewDTO reviewDTO, Room room, HttpSession session) {

        Member findMember = getMember(session);

        Review newReview = new Review(reviewDTO.getReviewId(), findMember, room,
                reviewDTO.getPros(), reviewDTO.getCons(), reviewDTO.getReviewTime(), reviewDTO.getScore());

        return reviewRepository.save(newReview);
    }

    @Transactional
    public void update(Long reviewId, ReviewUpdateDTO updateParam) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 찾기 실패 : reviewId를 찾을 수 없습니다.");
                });
        review.setPros(updateParam.getPros());
        review.setCons(updateParam.getCons());
        review.setScore(updateParam.getScore());
    }

    // 글 목록
    @Transactional(readOnly = true)
    public Page<Review> list(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    // 글 상세보기
    @Transactional(readOnly = true)
    public Review content(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("글 상세보기 실패 : reviewId를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
