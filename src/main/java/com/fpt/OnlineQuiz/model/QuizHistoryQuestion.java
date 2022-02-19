package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.model.idClasses.QuizHistoryQuestionId;
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
@IdClass(QuizHistoryQuestionId.class)
@Table(name = "QuizHistoryQuestion")
public class QuizHistoryQuestion {
    @Id
    @ManyToOne
    @JoinColumn(name = "quizHistoryId")
    private QuizHistory quizHistory;
    @Id
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "userAnswer")
    private Answer userAnswer;
}
