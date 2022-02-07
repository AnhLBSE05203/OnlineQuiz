package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Image")
public class Image {
    @Id
    @Column(name = "imageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "src")
    private String src;

    @Column(name = "status")
    private int status;

    @Column(name = "createdTime")
    private Date createdTime;

    @Column(name = "updatedTime")
    Date updatedTime;

    @Column(name = "createdUserId")
    private int createdUserId;

    @OneToMany(mappedBy = "profileImage")
    List<Account> accounts;

    @OneToMany(mappedBy = "thumbnail")
    List<Blog> blogs;
}
