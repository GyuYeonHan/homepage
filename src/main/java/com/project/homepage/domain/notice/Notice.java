package com.project.homepage.domain.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.homepage.domain.BaseTimeEntity;
import com.project.homepage.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String url;

    @Builder(access= AccessLevel.PRIVATE)
    private Notice(NoticeStatus status, User user, String message, String url) {
        this.status = status;
        this.user = user;
        this.message = message;
        this.url = url;
    }

    public static Notice create(String message, String url, User user) {
        return Notice.builder()
                .status(NoticeStatus.UNREAD)
                .user(user)
                .message(message)
                .url(url)
                .build();
    }

    public void read() {
        this.status = NoticeStatus.READ;
    }
}
