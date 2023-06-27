package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "posting")
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId; //시퀀스

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY) //Many = Posting, One = Member, 한 계정에 여러 개 글 작성
    @JoinColumn(name = "member_id")
    private Member member; // FK

    @Column(nullable = false, length = 50)
    private String title;

    //@Lob //대용량 데이터 4GB
    @Column(nullable = false, length = 500)
    private String content; //섬머노트 라이브러리

    @CreationTimestamp //시간 자동 입력
    private Timestamp postTime;

    private int postHits; //조회수

    /*@OneToMany(mappedBy = "posting", fetch = FetchType.EAGER) // 즉시로딩.. 실무에서는 지연로딩(LAZY)을 대부분 쓴다는데 일단 이걸로 놔둠
    private List<Reply> comment = new ArrayList<>();*/


    @Builder
    public Posting(Long postId, Member member, String title, String content, Timestamp postTime, int postHits) {
        this.postId = postId;
        this.member = member;
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.postHits = postHits;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

}