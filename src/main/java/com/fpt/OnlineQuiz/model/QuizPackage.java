package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QuizPackage")
public class QuizPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizPackageId")
    private int id;

    @Column(name = "quizPackageName")
    private String name;

    @Column(name = "createdTime")
    private Date createdTime;

    //() at the first and the end query is important
    @Formula("(SELECT COUNT(*) FROM quiz q WHERE q.quiz_package_id = quiz_package_id)")
    private Long quizCount;

    @Formula("(SELECT a.full_name FROM account a WHERE a.account_id = account_id)")
    private String acountName;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany(mappedBy = "quizHistory", cascade = CascadeType.ALL)
    private List<QuizHistoryQuestion> quizHistoryQuestions;
}
