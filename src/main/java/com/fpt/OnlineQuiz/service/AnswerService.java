package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.AnswerDTO;
import com.fpt.OnlineQuiz.model.Answer;

import java.util.List;


public interface AnswerService {
    void addAnswers(List<Answer> answers);

    List<Answer> getAnswers(int question_id);

    void updateAnswers(List<Answer> answers);

    void deleteAnswerByQuestionId(int questionId);

    List<AnswerDTO> getAnswersDTO(int question_id);

    List<Answer> getAllAnswer();

    List<AnswerDTO> getAllAnswerDTO();
}
