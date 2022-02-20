package com.fpt.OnlineQuiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "screenId")
    private int id;
    @Column(name = "screenName")
    private String name;
    @Column(name = "screenLink")
    private String link;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ScreenRole", joinColumns = @JoinColumn(name = "screenId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;

    public void addRole(Role role) {
        roles.add(role);
        role.getScreens().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getScreens().remove(this);
    }
}
