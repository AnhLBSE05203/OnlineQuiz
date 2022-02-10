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
@Table(name = "Course")
public class Course {
    @Id
    @Column(name = "courseId")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "lessonTotal")
    private int lessonTotal;
    @Column(name = "price")
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectId")
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "AccountCourse", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "accountId"))
    private List<Account> accounts;
    @OneToMany(mappedBy = "course")
    private List<PurchaseHistory> purchaseHistories;
}
