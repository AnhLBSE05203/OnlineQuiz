package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.utils.Constants;
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
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectId")
    private int id;
    @Column(name = "subjectName")
    private String name;
    @Column(name = "status")
    private int status;
    @OneToMany(mappedBy = "subject")
    private List<Course> courses;

    @OneToMany(mappedBy = "subject")
    private List<Question> questions;

    @OneToMany(mappedBy = "subject")
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imageId")
    private Image image;

    @OneToMany(mappedBy = "subject")
    private List<Lesson> lessons;
    @ManyToMany(mappedBy = "subjects")
    private List<Expert> experts;

    public void addExpert(Expert expert) {
        experts.add(expert);
        expert.getSubjects().add(this);
    }

    public SubjectAdminDTO toSubjectAdminDTO(){
        SubjectAdminDTO subjectAdminDTO = new SubjectAdminDTO();
        subjectAdminDTO.setId(this.getId());
        subjectAdminDTO.setImgSrc(this.getImage().getSrc());
        subjectAdminDTO.setName(this.getName());
        subjectAdminDTO.setTotalCourse(this.getCourses().size());
        String statusStr = Constants.subjectStatusConversion.get(this.getStatus());
        subjectAdminDTO.setStatusStr(statusStr);
        subjectAdminDTO.setStatus(this.getStatus());
        return subjectAdminDTO;
    }
}