package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFeaturedDTO {
    private int id;
    private String courseName;
    private String subjectName;
    private String description;
    private double price;
    private String imgSrc;
}
