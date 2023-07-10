package com.example.demo1.service;

import com.example.demo1.dto.RoomDTO;
import com.example.demo1.dto.member.MemberResponseDTO;
import com.example.demo1.dto.posting.PostingResponseDTO;
import com.example.demo1.dto.review.ReviewDTO;

import com.example.demo1.dto.review.ReviewResponseDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.entity.Review;
import com.example.demo1.entity.Room;
import com.example.demo1.repository.ReviewRepository;
import com.example.demo1.repository.S3Repository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final S3Repository s3Repository;

    public Review findMemberByReviewId(Long review_id){
        return reviewRepository.findById(review_id).get();
    }

    @Transactional
    public boolean addReview(ReviewDTO reviewDTO){
        if(existReview(reviewDTO)){
            throw new IllegalArgumentException("이미 리뷰를 작성했습니다.");
        } else{
            reviewRepository.save(reviewDTO.toEntity());
            return true;
        }
    }

    /*존재하면 true, 존재하지않으면 false*/
    private boolean existReview(ReviewDTO reviewDTO){
        return reviewRepository.findByMemberAndRoom(reviewDTO.getMember(), reviewDTO.getRoom()).isPresent();
    }

    @Transactional
    public boolean deleteReview(ReviewDTO reviewDTO){
        if(existReview(reviewDTO)){
            Review review = reviewRepository.findByMemberAndRoom(reviewDTO.getMember(), reviewDTO.getRoom()).get();
            reviewRepository.delete(review);
            return true;
        } else{
            throw new IllegalArgumentException("이미 삭제된 리뷰입니다.");
        }
    }

    public List<ReviewResponseDTO> search(Long room_id){
        List<Review> reviews = reviewRepository.findByRoomId(room_id);
        if(reviews.isEmpty()){
            throw new IllegalArgumentException("존재하지않는 방입니다.");
        }
        List<ReviewResponseDTO> reviewDtoList = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponseDTO reviewResponseDTO = ReviewResponseDTO.builder()
                    .review_id(review.getReview_id())
                    .member_id(review.getMember().getId())
                    .nickname(review.getMember().getNickname())
                    .room(review.getRoom())
                    .pros(review.getPros())
                    .cons(review.getCons())
                    .score(review.getScore())
                    .postTime(review.getPostTime())
                    .url(review.getUrl())
                    .build();

            reviewDtoList.add(reviewResponseDTO);
        }
        return reviewDtoList;

    }



}
