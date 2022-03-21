package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.AnswerDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "answerId")
    private int id;
    @Column(name = "answer")
    private String answer;

    @Column(name = "isCorrect")
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @OneToMany(mappedBy = "userAnswer")
    private List<QuizHistoryQuestion> quizHistoryQuestions;

    public AnswerDTO toAnswerDTO() {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(this.getId());
        answerDTO.setAnswer(this.getAnswer());
        answerDTO.setCorrect(this.isCorrect());
        answerDTO.setQuestion(this.question.toQuestionDTO());
        return answerDTO;
    }
}
