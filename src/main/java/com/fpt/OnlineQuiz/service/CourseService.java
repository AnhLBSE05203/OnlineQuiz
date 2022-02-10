package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Course;

import java.util.List;

public interface CourseService {
    public List<Course> getTopCourses(int number);
}
