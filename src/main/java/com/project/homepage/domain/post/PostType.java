package com.project.homepage.domain.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {

    ANNOUNCEMENT("POST_ANNOUNCEMENT", "공지사항", "/announcement"),
    QUESTION("POST_QUESTION", "질문", "/question");

    private final String key;
    private final String type;
    private final String url;
}