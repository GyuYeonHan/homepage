package com.project.homepage.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER", "일반 사용자"),
    STUDENT("ROLE_STUDENT", "학생"),
    PARENT("ROLE_PARENT", "학부모"),
    TEACHER("ROLE_TEACHER", "선생님"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String role;
}
