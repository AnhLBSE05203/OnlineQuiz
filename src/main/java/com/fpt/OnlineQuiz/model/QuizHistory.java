package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QuizHistory")
public class QuizHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizHistoryId")
    private int id;

    @Column(name = "quizHistoryName")
    private String name;

    @Column(name = "createdTime")
    private Date createdTime;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    //() at the first and the end query is important
    @Formula("(SELECT COUNT(*) FROM question q WHERE q.quiz_history_id = quiz_history_id)")
    private Long quizCount;

    @Formula("(SELECT a.full_name FROM account a WHERE a.account_id = account_id)")
    private String acountName;

    @OneToMany(mappedBy = "quizHistory", cascade = CascadeType.ALL)
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
