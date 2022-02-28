package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuesitonBySubjectId(int subject_id);

    Question getQuestionByQuestionId(int question_id);

    Page<QuestionAdminDTO> getByPagingRequest(PagingRequest pagingRequest);

    void addQuestion(Question question);

    void updateQuestion(Question question);

    QuestionAdminDTO getQuestionDTOById(int id);

    void deleteQuestion(int question_id);
}
