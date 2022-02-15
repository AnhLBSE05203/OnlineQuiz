package com.fpt.OnlineQuiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Account")
@Where(clause = "status != 0")
@JsonIgnoreProperties(value= {"purchaseHistories", "quizHistories", "courses","tokens","reviews"})
public class Account implements UserDetails {

    private static final long serialVersionUID = -3164082858501464263L;

    @Id
    @Column(name = "accountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "gender")
    private int gender;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AccountRole", joinColumns = @JoinColumn(name = "accountId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;

    @OneToMany(mappedBy = "account")
    private List<PurchaseHistory> purchaseHistories;
    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image profileImage;
    @ManyToMany(mappedBy = "accounts")
    private List<Course> courses;
    @OneToMany(mappedBy = "account")
    private List<Token> tokens;
    @OneToMany(mappedBy = "account")
    private List<Review> reviews;
    @OneToMany(mappedBy = "account")
    private List<QuizHistory> quizHistories;
    @OneToOne(mappedBy = "account")
    private Expert expert;
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getAccounts().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getAccounts().remove(this);
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.getAccounts().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getAccounts().remove(this);
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
