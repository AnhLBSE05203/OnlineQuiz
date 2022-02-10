package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Expert")
public class Expert implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ExpertSubject", joinColumns = @JoinColumn(name = "expertId"), inverseJoinColumns = @JoinColumn(name = "subjectId"))
    private List<Subject> subjects;
}
