package com.fpt.OnlineQuiz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

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


    @Autowired
    public QuizHistory(String name,Date date,int id,long count,String accountName){
        this.name = name;
        this.createdTime = date;
        this.id = id;
        this.quizCount = count;
        this.accountName = accountName;

    }

    @Autowired
    public QuizHistory(String name,Date date,int number,long trueNum){
        this.name = name;
        this.historyTime = date;
        this.number = number;
        this.trueNum = trueNum;
    }
    private long trueNum;

    @Column(name = "createdTime")
    private Date createdTime;
    @Column(name = "historyTime")
    private Date historyTime;
    @Column(name = "numOfQuestion")
    private Integer number;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "historyAccountId")
    private Account accountHistory;
    //() at the first and the end query is important
    @Formula("(SELECT COUNT(*) FROM question q WHERE q.quiz_history_id = quiz_history_id)")
    private Long quizCount;

    @Formula("(SELECT a.full_name FROM account a WHERE a.account_id = account_id)")
    private String accountName;

    @Formula("(10)")
    private Long ahihi;


    @OneToMany(mappedBy = "quizHistory", cascade = CascadeType.ALL)
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
