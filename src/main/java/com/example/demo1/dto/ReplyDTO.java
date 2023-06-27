package com.example.demo1.dto;

import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.entity.Reply;
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
public class ReplyDTO {

    private Long commentId;
    private Posting posting; //FK
    private Member member; //FK

    @NotBlank(message = "댓글을 입력해주세요")
    @Pattern(regexp = "^.{2,50}$", message = "글자수는 2자 이상 50자 이하로 작성해주세요")
    private String content;

    private Timestamp commentTime;

    public Reply toEntity() {
        return Reply.builder()
                .commentId(commentId)
                .posting(posting)
                .member(member)
                .content(content)
                .commentTime(commentTime)
                .build();
    }

    @Builder
    public ReplyDTO(Long commentId, Posting posting, Member member, String content, Timestamp commentTime) {
        this.commentId = commentId;
        this.posting = posting;
        this.member = member;
        this.content = content;
        this.commentTime = commentTime;
    }
}