package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
public class Question {
    @Id
    @Column(name = "questionId")
    private int id;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
    @Column(name = "explanation")
    private String explanation;
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;
    @OneToMany(mappedBy = "question")
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}