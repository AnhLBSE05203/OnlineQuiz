package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.QuestionRepository;
import com.fpt.OnlineQuiz.model.Question;
import com.fpt.OnlineQuiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuesitonBySubjectId(int subject_id) {
        return questionRepository.getQuestionsBySubjectId(subject_id);
    }
}
