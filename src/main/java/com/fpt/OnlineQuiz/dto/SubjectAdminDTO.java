package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAdminDTO {
    private int id;
    private String imgSrc;
    private String name;
    private long totalCourse;
    private String statusStr;
    private int status;
    private String subjectInfo;
    private String learnAfter;
}
