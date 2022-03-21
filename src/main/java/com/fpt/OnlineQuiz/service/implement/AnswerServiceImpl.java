package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.AnswerRepository;
import com.fpt.OnlineQuiz.dto.AnswerDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void addAnswers(List<Answer> answers) {
        answerRepository.addAnswers(answers);
    }

    @Override
    public List<Answer> getAnswers(int questionId) {
        return (List<Answer>) answerRepository.getAnswersByQuestionId(questionId);
    }

    @Override
    public List<AnswerDTO> getAnswersDTO(int question_id) {
        List<Answer> answers = (List<Answer>)answerRepository.getAnswersByQuestionId(question_id);
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        for (Answer answer: answers) {
            AnswerDTO answerDTO = answer.toAnswerDTO();
            answerDTOList.add(answerDTO);
        }
        return answerDTOList;
    }

    @Override
    public List<Answer> getAllAnswer() {
        return (List<Answer>) answerRepository.getAllAnswer();
    }
    @Override
    public List<AnswerDTO> getAllAnswerDTO() {
        List<Answer> answers = (List<Answer>) answerRepository.getAllAnswer();
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        for (Answer answer: answers) {
            AnswerDTO answerDTO = answer.toAnswerDTO();
            answerDTOList.add(answerDTO);
        }
        return answerDTOList;
    }

    @Override
    public void updateAnswers(List<Answer> answers) {
        answerRepository.updateAnswers(answers);
    }

    @Override
    public void deleteAnswerByQuestionId(int questionId) {
        answerRepository.deleteAnswersByQuestionId(questionId);
    }


}
