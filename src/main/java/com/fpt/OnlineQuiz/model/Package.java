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
@Table(name = "Package")
public class Package {
    @Id
    @Column(name = "packageId")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lessonTotal")
    private int lessonTotal;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "AccountPackage", joinColumns = @JoinColumn(name = "packageId"), inverseJoinColumns = @JoinColumn(name = "accountId"))
    private List<Account> accounts;
    @OneToMany(mappedBy = "subjectPackage")
    private List<PurchaseHistory> purchaseHistories;
}
