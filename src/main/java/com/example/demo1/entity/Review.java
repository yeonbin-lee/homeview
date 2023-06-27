package com.example.demo1.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne //Many = Review, One = Member, 한 계정에 여러 리뷰 작성
    @JoinColumn(name = "member_id")
    private Member member; // FK

    @ManyToOne // Many = Riview, One = Room, 한 방에 여러 리뷰 작성
    @JoinColumn(name = "room_id")
    private Room room; // FK

    //@Lob //대용량 데이터 4GB
    @Column(nullable = false, length = 100)
    private String pros;

    //@Lob //대용량 데이터 4GB
    @Column(nullable = false, length = 100)
    private String cons;

    @CreationTimestamp //시간 자동 입력
    private Timestamp reviewTime;

    private int score;

    @Builder
    public Review(Long reviewId, Member member, Room room, String pros, String cons, Timestamp reviewTime, int score) {
        this.reviewId = reviewId;
        this.member = member;
        this.room = room;
        this.pros = pros;
        this.cons = cons;
        this.reviewTime = reviewTime;
        this.score = score;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
