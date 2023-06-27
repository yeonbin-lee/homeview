package com.example.demo1.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "comment")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posting posting; //FK

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //FK

    @Column(nullable = false, length = 50)
    private String content;

    @CreationTimestamp
    private Timestamp commentTime;


    @Builder
    public Reply(Long comment_id, Posting posting, Member member, String content, Timestamp commentTime) {
        this.comment_id = comment_id;
        this.posting = posting;
        this.member = member;
        this.content = content;
        this.commentTime = commentTime;
    }

    public void setContent(String comment) {
        this.content = comment;
    }
}
