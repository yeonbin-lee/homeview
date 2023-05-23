package com.example.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "어드민"),
    MEMBER("ROLE_MEMBER", "일반 사용자");

    private String key;
    private String title;
}
