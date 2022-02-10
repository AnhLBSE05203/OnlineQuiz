
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
@Table(name = "Blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blogId")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private int status;

    @Column(name = "createdTime")
    private Date createdTime;

    @Column(name = "updatedTime")
    Date updatedTime;

    @Column(name = "createdUserId")
    private int createdUserId;
    @Column(name = "updatedUserId")
    private int updatedUserId;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image thumbnail;

}
