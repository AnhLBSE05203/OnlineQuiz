package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.model.Course;

import java.util.List;

public interface CourseService {
    public List<CourseFeaturedDTO> getFeaturedCourses(int number);

    List<Course> getNext3Courses(int accountId, int start);

    List<Course> getTop3Courses(int accountId);

    List<Course> getCoursesRegistration(int accountId);
}
