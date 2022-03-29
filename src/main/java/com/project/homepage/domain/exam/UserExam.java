package com.project.homepage.domain.exam;

import com.project.homepage.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_exam_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<User> studentList = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Exam> examList = new ArrayList<>();

    @Column(nullable = false)
    private String title;
}
