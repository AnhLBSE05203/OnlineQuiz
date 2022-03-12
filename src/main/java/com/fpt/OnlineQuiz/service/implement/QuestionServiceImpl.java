package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.QuestionRepository;
import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionByLessonId(int lessonId) {
        return questionRepository.getQuestionsByLessonId(lessonId);
    }

    @Override
    public Question getQuestionByQuestionId(int questionId) {
        return questionRepository.getQuestionByQuestionId(questionId);
    }

    @Override
    public Page<QuestionAdminDTO> getByPagingRequest(PagingRequest pagingRequest) {
        List<Question> questions = questionRepository.getByPagingRequest(pagingRequest);
        long count = questionRepository.getQuestionCountByPagingRequest(pagingRequest);
        List<QuestionAdminDTO> questionAdminDTOs = new ArrayList<>();
        // convert Subject to SubjectAdminDTO
        for (Question question : questions) {
            QuestionAdminDTO questionAdminDTO = question.toQuestionAdminDTO();
            questionAdminDTOs.add(questionAdminDTO);
        }
        // convert List to Page
        Page<QuestionAdminDTO> page = new Page<>(questionAdminDTOs);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    @Override
    public void addQuestion(Question question) {
        questionRepository.addQuestion(question);
    }

    @Override
    public void updateQuestion(Question question) {
        questionRepository.updateQuestion(question);
    }

    @Override
    public QuestionAdminDTO getQuestionDTOById(int id) {
        Question question = questionRepository.getQuestionByQuestionId(id);
        QuestionAdminDTO questionAdminDTO = question.toQuestionAdminDTO();
        return questionAdminDTO;
    }

    @Override
    public void deleteQuestion(int questionId) {
        questionRepository.deleteQuestion(questionId);
    }
}