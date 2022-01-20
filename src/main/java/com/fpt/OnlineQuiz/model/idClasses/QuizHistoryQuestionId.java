package com.fpt.OnlineQuiz.model.idClasses;

import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.QuizHistory;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QuizHistoryQuestionId implements Serializable {
    private QuizHistory quizHistory;
    private Question question;
}
