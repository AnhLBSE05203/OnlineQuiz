package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PurchaseHistory")
public class PurchaseHistory {
    @Id
    @Column(name = "purchaseId")
    private int id;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "packageId")
    private Package subjectPackage;
}
