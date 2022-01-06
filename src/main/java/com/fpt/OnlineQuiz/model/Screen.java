package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Screen {
    @Id
    @Column(name = "screenId")
    private int id;

    @Column(name = "screenName")
    private String name;

    @Column(name = "screenLink")
    private String link;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RoleScreen", joinColumns = @JoinColumn(name = "screenId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;
}
