package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerId")
    private int id;
    @Column(name = "answer")
    private String answer;
    @Column(name = "explanation")
    private String explanation;

    @Column(name = "isCorrect")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @OneToMany(mappedBy = "userAnswer")
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
