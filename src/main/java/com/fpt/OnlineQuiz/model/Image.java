package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Image")
@Where(clause = "status != 0")
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

    @OneToMany(mappedBy = "image")
    List<Subject> subjects;

    public void setDefaultImg() {
        Date now = new Date();
        id = Constants.DEFAULT_SUBJECT_IMAGE_ID;
        status = Constants.STATUS_DEFAULT;
        createdTime = now;
        updatedTime = now;
        createdUserId = 1;
    }
}
