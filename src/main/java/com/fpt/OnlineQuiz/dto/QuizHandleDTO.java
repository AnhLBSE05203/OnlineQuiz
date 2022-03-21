package com.fpt.OnlineQuiz.dto;

import com.fpt.OnlineQuiz.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizHandleDTO {
    private List<AnswerDTO> answerDTOList;
    private int count;
    private int lessid;
    private QuestionDTO questionDTO;
}
