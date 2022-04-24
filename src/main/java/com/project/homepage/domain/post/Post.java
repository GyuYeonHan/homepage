package com.project.homepage.domain.post;

import com.project.homepage.domain.BaseTimeEntity;
import com.project.homepage.domain.Comment;
import com.project.homepage.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PostType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(Long id, PostType type, User user, String title, String content) {
        this.id = id;
        this.type = type;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
