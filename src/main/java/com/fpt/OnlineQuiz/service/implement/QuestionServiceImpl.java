package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.AnswerRepository;
import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDQuestionRepository;
import com.fpt.OnlineQuiz.dao.QuestionRepository;
import com.fpt.OnlineQuiz.dto.AnswerDTO;
import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.AnswerService;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerService answerService;
    @Autowired
    private CRUDQuestionRepository crudQuestionRepository;

    @Override
    public List<Question> getQuestionByLessonId(int lessonId) {
        return questionRepository.getQuestionsByLessonId(lessonId);
    }
    public List<QuestionDTO> getQuestionByLessonIdDTO(int lessonId) {
        List<Question> questionList = questionRepository.getQuestionsByLessonId(lessonId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question questionL:questionList) {
            QuestionDTO questionDTO = questionL.toQuestionDTO();
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    @Override
    public int findAnswerIcCorrect(int questionId) {
       // QuestionDTO questionDTO = questionRepository.getQuestionByQuestionIdDto(questionId);
        List<AnswerDTO> answerDTOList = answerService.getAnswersDTO(questionId);
        for (AnswerDTO answerDTO: answerDTOList) {
            if(answerDTO.isCorrect()){
                return answerDTO.getId();
            }
        }
        return -1;
    }

    @Override
    public Question getQuestionByQuestionId(int questionId) {
        return questionRepository.getQuestionByQuestionId(questionId);
    }
    public Question getQuestionByQuestionIdDTO(int questionId) {
        questionRepository.getQuestionByQuestionId(questionId);
        return questionRepository.getQuestionByQuestionId(questionId);
    }

    @Override
    public Page<QuestionAdminDTO> getByPagingRequest(PagingRequest pagingRequest, int lessonId) {
        List<Question> questions = questionRepository.getByPagingRequest(pagingRequest, lessonId);
        long count = questionRepository.getQuestionCountByPagingRequest(pagingRequest, lessonId);
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

    @Override
    public List<Question> getQuestionQHid(int id) {
        return crudQuestionRepository.findByQuizHistoryId(id);
    }

    @Override
    public int countQuestionByLessonId(int lessonId) {
        return questionRepository.countQuestionByLessonId(lessonId);
    }

    @Override
    public List<Question> getNumberOfQuestionByLessonId(int lessonId, int number) {
        return  questionRepository.getNumberOfQuestionByLessonId(lessonId, number);
    }
}