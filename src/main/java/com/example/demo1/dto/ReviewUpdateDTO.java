package com.example.demo1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewUpdateDTO {

    @NotBlank(message = "장점을 입력해주세요")
    @Pattern(regexp = "^.{2,100}$", message = "글자수는 2자 이상 100자 이하로 작성해주세요")
    private String pros;

    @NotBlank(message = "단점을 입력해주세요")
    @Pattern(regexp = "^.{2,100}$", message = "글자수는 2자 이상 100자 이하로 작성해주세요")
    private String cons;

    private int score;

    public ReviewUpdateDTO() {
    }

    public ReviewUpdateDTO(String pros, String cons, int score) {
        this.pros = pros;
        this.cons = cons;
        this.score = score;
    }
}