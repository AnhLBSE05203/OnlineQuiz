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
@Table(name = "QuizPackage")
public class QuizPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizPackageId")
    private int id;

    @Column(name = "quizPackageName")
    private String name;

    @Column(name = "createdTime")
    private Date createdTime;

    @OneToMany
    @JoinColumn(name = "quizId")
    private List<Quiz> quiz;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany(mappedBy = "quizHistory", cascade = CascadeType.ALL)
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
