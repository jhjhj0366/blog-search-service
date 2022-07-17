package com.search.blog.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    public String getRole() {
        return PREFIX + name();
    }
}
