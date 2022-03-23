package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId")
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "datetime")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account accountid;
    @ManyToOne
    @JoinColumn(name = "blogId")
    private Blog blogid;
}
