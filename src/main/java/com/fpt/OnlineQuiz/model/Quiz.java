package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QuizId")
    private int id;

    @Column(name = "quizQuestion")
    private String question;

    @Column(name = "quizAnswer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quizPackageId")
    private QuizPackage quizPackage;

}
