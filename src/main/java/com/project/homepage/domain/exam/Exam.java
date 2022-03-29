package com.project.homepage.domain.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private List<Exam> studentList = new ArrayList<>();

    @Column(nullable = false)
    private String name;

}
