package com.fpt.OnlineQuiz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonAdminDTO {
    private int lesId;
    private String name;
    private String lessonType;
    private String subjects;
    private String content;
    private String status;
    private String time;
}
