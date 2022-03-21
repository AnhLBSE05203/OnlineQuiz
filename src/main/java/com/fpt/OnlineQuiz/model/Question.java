package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.QuestionAdminDTO;
import com.fpt.OnlineQuiz.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "questionId")
    private int id;
    @Column(name = "question")
    private String question;
    @Column(name = "explanation")
    private String explain;
    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "lessonId")
    private Lesson lesson;
    @OneToMany(mappedBy = "question")
    private List<QuizHistoryQuestion> quizHistoryQuestions;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @ManyToOne
    @JoinColumn(name = "quizHistoryId")
    private QuizHistory quizHistory;

    public QuestionAdminDTO toQuestionAdminDTO() {
        QuestionAdminDTO questionAdminDTO = new QuestionAdminDTO();
        questionAdminDTO.setId(this.getId());
        questionAdminDTO.setQuestion(this.getQuestion());
        questionAdminDTO.setAnswer(this.getAnswer());
        questionAdminDTO.setExplain(this.getExplain());
        return questionAdminDTO;
    }
    public QuestionDTO toQuestionDTO() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(this.getId());
        questionDTO.setQuestion(this.getQuestion());
        questionDTO.setAnswer(this.getAnswer());
        questionDTO.setExplain(this.getExplain());
        return questionDTO;
    }
}
