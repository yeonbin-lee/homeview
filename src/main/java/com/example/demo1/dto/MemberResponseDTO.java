package com.example.demo1.dto;

import com.example.demo1.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class MemberResponseDTO {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String role;

    @Builder
    public MemberResponseDTO(Member member){
            this.id = member.getId();
            this.name = member.getName();
            this.nickname = member.getNickname();
            this.email = member.getEmail();
            this.role = member.getRole();
        }
}
