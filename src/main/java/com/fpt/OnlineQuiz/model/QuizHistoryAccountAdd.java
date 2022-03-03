package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QuizHistoryAccountAdd")
public class QuizHistoryAccountAdd {
    /*This table is find Those QuizHistory(1 quizHistory contain multiple questions)
    *existed in user account ,user add them to there practices list */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizHistoryAccountAddId")
    private int id;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "quizHistoryId")
    private QuizHistory quizHistory;
}
