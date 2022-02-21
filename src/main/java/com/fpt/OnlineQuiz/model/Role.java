package com.fpt.OnlineQuiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Role")
@Where(clause = "status != 0")
@JsonIgnoreProperties(value = {"accounts", "screens"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleId")
    private int id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "createdTime")
    private Date createdTime;

    @Column(name = "updatedTime")
    Date updatedTime;

    @Column(name = "createdUserId")
    private int createdUserId;
    @Column(name = "updatedUserId")
    private int updatedUserId;
    @Column(name = "status", nullable = false)
    private int status;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<Screen> screens;

    public void addAccount(Account account) {
        accounts.add(account);
        account.getRoles().add(this);
    }

    public void removeRole(Account account) {
        accounts.remove(account);
        account.getRoles().remove(this);
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
        screen.getRoles().add(this);
    }

    public void removeScreen(Screen screen) {
        screens.remove(screen);
        screen.getRoles().remove(this);
    }

}
