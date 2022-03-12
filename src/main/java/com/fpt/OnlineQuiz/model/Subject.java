package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.utils.Constants;
import com.fpt.OnlineQuiz.utils.Utils;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subjectId")
    private int id;
    @Column(name = "subjectName")
    private String name;
    @Column(name = "status")
    private int status;
    @Column(name = "subjectInfo")
    private String subjectInfo;
    @Column(name = "learnAfter")
    private String learnAfter;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Course> courses;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imageId")
    private Image image;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Lesson> lessons;
    @ManyToMany(mappedBy = "subjects")
    private List<Expert> experts;

    public void addExpert(Expert expert) {
        experts.add(expert);
        expert.getSubjects().add(this);
    }

    public SubjectAdminDTO toSubjectAdminDTO() {
        SubjectAdminDTO subjectAdminDTO = new SubjectAdminDTO();
        subjectAdminDTO.setId(this.getId());
        if (this.getImage() != null) {
            subjectAdminDTO.setImgSrc(this.getImage().getSrc());
        }
        subjectAdminDTO.setName(this.getName());
        if (this.getCourses() != null) {
            subjectAdminDTO.setTotalCourse(this.getCourses().size());
        }
        String statusStr = Constants.subjectStatusConversion.get(this.getStatus());
        subjectAdminDTO.setStatusStr(statusStr);
        subjectAdminDTO.setStatus(this.getStatus());
        subjectAdminDTO.setSubjectInfo(this.getSubjectInfo());
        subjectAdminDTO.setLearnAfter(this.getLearnAfter());
        return subjectAdminDTO;
    }

    public void setFromSubjectAdminDTO(SubjectAdminDTO subjectAdminDTO) {
        Utils.copyNonNullProperties(subjectAdminDTO, this);
        // this only includes (id, name, subjectInfo, learnAfter, status)
        // image needs to be handled separately
    }
}