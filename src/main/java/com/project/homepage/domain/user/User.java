package com.project.homepage.domain.user;

import com.project.homepage.domain.BaseTimeEntity;
import com.project.homepage.domain.Comment;
import com.project.homepage.domain.post.Post;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @Builder
    public User(Long id, Role role, String username, String loginId, String password) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.loginId = loginId;
        this.password = password;
    }
}
