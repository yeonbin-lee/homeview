package com.example.demo1.dto;

import com.example.demo1.entity.Member;
import com.example.demo1.entity.Review;
import com.example.demo1.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Setter
public class ReviewDTO {
    private Long reviewId;
    private Member member; // FK
    private Room room; // FK

    @NotBlank(message = "장점을 입력해주세요")
    @Pattern(regexp = "^.{2,100}$", message = "글자수는 2자 이상 100자 이하로 작성해주세요")
    private String pros;

    @NotBlank(message = "단점을 입력해주세요")
    @Pattern(regexp = "^.{2,100}$", message = "글자수는 2자 이상 100자 이하로 작성해주세요")
    private String cons;

    private Timestamp reviewTime;
    private int score;

    public Review toEntity() {
        return Review.builder()
                .reviewId(reviewId)
                .member(member)
                .room(room)
                .pros(pros)
                .cons(cons)
                .reviewTime(reviewTime)
                .score(score)
                .build();
    }

    @Builder
    public ReviewDTO(Long reviewId, Member member, Room room, String pros, String cons, Timestamp reviewTime, int score) {
        this.reviewId = reviewId;
        this.member = member;
        this.room = room;
        this.pros = pros;
        this.cons = cons;
        this.reviewTime = reviewTime;
        this.score = score;
    }
}