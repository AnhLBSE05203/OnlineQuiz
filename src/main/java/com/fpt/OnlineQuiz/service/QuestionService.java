package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuesitonBySubjectId(int subject_id);

    Question getQuestionByQuestionId(int question_id);

    void addQuestion(Question question);

    void updateQuestion(Question question);
}
