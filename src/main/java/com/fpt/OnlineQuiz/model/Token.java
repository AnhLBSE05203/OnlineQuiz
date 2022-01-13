package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId")
    private int id;
    @Column(name = "tokenString", nullable = false)
    private String tokenString;
    @Column(name = "tokenType", nullable = false)
    private String tokenType;
    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
