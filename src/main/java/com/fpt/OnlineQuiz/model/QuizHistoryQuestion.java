package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.model.idClasses.QuizHistoryQuestionId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
