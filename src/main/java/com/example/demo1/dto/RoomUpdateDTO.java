package com.example.demo1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUpdateDTO {

    @NotBlank(message = "총 점의 평균입니다")
    @Pattern(regexp = "^.{1,5}$", message = "점수는 1이상 5이하여야 합니다")
    private double totalScore;

    public RoomUpdateDTO() {
    }

    public RoomUpdateDTO(double totalScore) {
        this.totalScore = totalScore;
    }
}