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

    @Column(name = "description")
    private String des;

//    @Autowired
//    public QuizHistory(String name,Date date,int id,long count,String accountName){
//        this.name = name;
//        this.createdTime = date;
//        this.id = id;
//        this.quizCount = count;
//        this.accountName = accountName;
//
//    }
//
//    @Autowired
//    public QuizHistory(String name,Date date,int number,long trueNum){
//        this.name = name;
//        this.historyTime = date;
//        this.number = number;
//        this.trueNum = trueNum;
//    }
//    @Column
//    private long trueNum;

    @Column(name = "createdTime")
    private Date createdTime;
    @Column(name = "historyTime")
    private Date historyTime;
    @Column(name = "numOfQuestion",nullable = true)
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

    @Formula("(SELECT COUNT(a.is_correct) FROM quiz_history qh INNER JOIN quiz_history_question qhq\n" +
            "    on qh.quiz_history_id = qhq.quiz_history_id\n" +
            "INNER JOIN answer a on a.answer_id = qhq.user_answer\n" +
            "WHERE qh.history_account_id = history_account_id AND qh.quiz_history_id = quiz_history_id AND a.is_correct = true\n" +
            "GROUP BY qh.quiz_history_id)")
    private Long trueNum;
    @Formula("(SELECT COUNT(*) FROM quiz_history_question qhq INNER JOIN quiz_history qh on qh.quiz_history_id = qhq.quiz_history_id\n" +
            "WHERE qh.quiz_history_id = quiz_history_id)")
    private Long totalQues;

    @OneToMany(mappedBy = "quizHistory", cascade = CascadeType.ALL)
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
