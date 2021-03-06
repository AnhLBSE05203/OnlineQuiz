package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courseId")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Formula("(SELECT COUNT(*) FROM lesson l WHERE l.courseid = course_id)")
    private long lessonTotal;
    @Column(name = "price")
    private double price;
    @Column(name = "status")
    private int status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectId")
    private Subject subject;
    @OneToMany(mappedBy = "course")
    List<Lesson> lessons;
    @ManyToMany
    @JoinTable(name = "AccountCourse", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "accountId"))
    private List<Account> accounts;
    @Formula("(SELECT COUNT(*) FROM account_course ac WHERE ac.course_id = course_id)")
    private long enrollTotal;
    @OneToMany(mappedBy = "course")
    private List<PurchaseHistory> purchaseHistories;

    public void addAccount(Account account) {
        accounts.add(account);
        account.getCourses().add(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCourses().remove(this);
    }

    public CourseUserDTO toCourseUserDTO() {
        CourseUserDTO courseUserDTO = new CourseUserDTO();
        courseUserDTO.setId(this.getId());
        if (this.getSubject().getImage() != null) {
            courseUserDTO.setImgSrc(this.getSubject().getImage().getSrc());
        }
        courseUserDTO.setCourseName(this.getName());
        courseUserDTO.setSubjectName(this.getSubject().getName());
        courseUserDTO.setDescription(this.getDescription());
        courseUserDTO.setPrice(this.getPrice());
        courseUserDTO.setLessonTotal(this.getLessonTotal());
        return courseUserDTO;
    }

    public CourseAdminDTO toCourseAdminDTO() {
        CourseAdminDTO courseAdminDTO = new CourseAdminDTO();
        courseAdminDTO.setId(this.getId());
        courseAdminDTO.setName(this.getName());
        courseAdminDTO.setSubjectName(this.getSubject().getName());
        courseAdminDTO.setDescription(this.getDescription());
        courseAdminDTO.setPrice(this.getPrice());
        courseAdminDTO.setLessonTotal(this.getLessonTotal());
        courseAdminDTO.setSubjectName(this.getSubject().getName());
        courseAdminDTO.setSubjectId(this.getSubject().getId());
        courseAdminDTO.setEnrollTotal(this.getEnrollTotal());
        String statusStr = Constants.courseStatusConversion.get(this.getStatus());
        courseAdminDTO.setStatusStr(statusStr);
        courseAdminDTO.setStatus(this.getStatus());
        return courseAdminDTO;
    }

    public void setFromCourseAdminDTO(CourseAdminDTO courseAdminDTO) {
        name = courseAdminDTO.getName();
        description = courseAdminDTO.getDescription();
        price = courseAdminDTO.getPrice();
        status = courseAdminDTO.getStatus();
        // this doesn't handle Subject fields
    }
}
