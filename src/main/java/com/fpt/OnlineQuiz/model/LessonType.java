package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LessonType")
public class LessonType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "typeId")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "lessonType")
    private List<Lesson> lessons;
}
