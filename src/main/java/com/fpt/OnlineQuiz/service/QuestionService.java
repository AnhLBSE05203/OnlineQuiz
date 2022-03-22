package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestionByLessonId(int lessonId);

    Question getQuestionByQuestionId(int questionId);

    Page<QuestionAdminDTO> getByPagingRequest(PagingRequest pagingRequest, int lessonId);

    void addQuestion(Question question);

    void updateQuestion(Question question);

    QuestionAdminDTO getQuestionDTOById(int id);

    void deleteQuestion(int questionId);

    List<Question> getQuestionQHid(int id);

    List<QuestionDTO> getQuestionByLessonIdDTO(int lessonId);

    int findAnswerIcCorrect(int questionId);
    int countQuestionByLessonId(int lessonId);

    List<Question> getNumberOfQuestionByLessonId(int lessonId, int number);

    List<Question> getQuestionQuizHistoryId(int id);

}
