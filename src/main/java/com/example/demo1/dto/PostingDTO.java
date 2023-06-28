package com.example.demo1.dto;

import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class PostingDTO {

    private Long post_id; //시퀀스
    private Member member;
    private String nickname;

    @NotBlank(message = "제목을 입력해주세요")
    @Pattern(regexp = "^.{2,50}$", message = "2 ~ 50 자리의 제목을 작성해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Pattern(regexp = "^.{2,500}$", message = "글자수는 2자 이상 500자 이하로 작성해주세요")
    private String content;

    private Timestamp postTime;
    private int postHits;
    //private List<Reply> comment;



    public Posting toEntity() {
        return Posting.builder()
                .post_id(post_id)
                .member(member)
                .nickname(nickname)
                .title(title)
                .content(content)
                .postTime(postTime)
                .postHits(0)
                .build();
    }

    @Builder
    public PostingDTO(Long post_id, Member member, String nickname, String title, String content, Timestamp postTime, int postHits/*, List<Reply> comment*/) {
        this.post_id = post_id;
        this.member = member;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.postTime = postTime;
        this.postHits = postHits;
        //this.comment = comment;
    }

}