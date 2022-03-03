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
