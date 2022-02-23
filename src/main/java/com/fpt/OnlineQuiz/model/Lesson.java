package com.fpt.OnlineQuiz.model;

import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
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
@Table(name = "Lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //
    @Column(name = "lessonId")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private String status;
    @Column(name = "time")
    private String time;


    @ManyToOne
    @JoinColumn(name = "typeId")
    private LessonType lessonType;

    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subject;


    public LessonAdminDTO toLessonAdminDTO() {
        LessonAdminDTO lessonAdminDTO = new LessonAdminDTO();
        lessonAdminDTO.setId(this.getId());
        lessonAdminDTO.setName(this.getName());
        lessonAdminDTO.setLessonType(this.lessonType.getName());
        lessonAdminDTO.setSubjects(this.subject.getName());
        lessonAdminDTO.setContent(this.getContent());
        lessonAdminDTO.setStatus(this.getStatus());
        lessonAdminDTO.setTime(this.getTime());
        return lessonAdminDTO;
    }
}
