package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseUserDTO {
    private int id;
    private String courseName;
    private String subjectName;
    private String description;
    private int lessonTotal;
    private double price;
    private String imgSrc;
}
