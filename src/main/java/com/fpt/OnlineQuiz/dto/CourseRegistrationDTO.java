package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationDTO {
    private int id;
    private String courseName;
    private double price;
}
