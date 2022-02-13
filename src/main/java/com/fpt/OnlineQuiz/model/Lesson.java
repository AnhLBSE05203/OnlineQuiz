package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lessonId")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private LessonType lessonType;

    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;

}
