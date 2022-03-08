package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseAdminDTO {
    private int id;
    private String name;
    private String subjectName;
    private int subjectId;
    private String description;
    private int lessonTotal;
    private double price;
    private int status;
    private String statusStr;
}
