package com.fpt.OnlineQuiz.dto;

import com.fpt.OnlineQuiz.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private int id;
    private String answer;
    private boolean isCorrect;
    private QuestionDTO question;

}
