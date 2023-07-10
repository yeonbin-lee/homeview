package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "s3")
public class S3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="s3_id")
    private Long s3_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String url;

    @Builder
    public S3(Long s3_id, Review review, String url){
        this.s3_id = s3_id;
        this.review = review;
        this.url = url;
    }

}
