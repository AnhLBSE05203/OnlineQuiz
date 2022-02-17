package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuesitonBySubjectId(int subject_id);
}
