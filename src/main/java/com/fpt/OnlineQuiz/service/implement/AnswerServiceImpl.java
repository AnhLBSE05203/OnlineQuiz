package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.AnswerRepository;
import com.fpt.OnlineQuiz.model.Answer;
import com.fpt.OnlineQuiz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Answer> getAnswers(int question_id) {
        return (List<Answer>) answerRepository.getAnswersByQuestionId(question_id);
    }
}
