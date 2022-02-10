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
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectId")
    private int id;
    @Column(name = "subjectName")
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Course> courses;

    @OneToMany(mappedBy = "subject")
    private List<Question> questions;

    @OneToMany(mappedBy = "subject")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imageId")
    private Image image;

    @OneToMany(mappedBy = "subject")
    private List<Lesson> lessons;
    @ManyToMany(mappedBy = "subjects")
    private List<Expert> experts;
}
