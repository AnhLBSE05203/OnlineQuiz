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
@Table(name = "QuizFolder")
public class QuizFolderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quizFolderId")
    private int id;

    @Column(name = "quizFolderName")
    private String name;

    @Column(name = "folderCreatedTime")
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
