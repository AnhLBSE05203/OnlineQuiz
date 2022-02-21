package com.fpt.OnlineQuiz.dto;


import com.fpt.OnlineQuiz.model.LessonType;
import com.fpt.OnlineQuiz.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonAdminDTO {
    private int LesId;
    private String name;
    private List<LessonType> lessonType;
    private List<Subject> subjects;
    private String content;
    private String status;
    private String time;
}
