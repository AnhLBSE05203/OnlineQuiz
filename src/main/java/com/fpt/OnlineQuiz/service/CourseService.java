package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.model.Course;

import java.util.List;

public interface CourseService {
    public List<CourseFeaturedDTO> getFeaturedCourses(int number);

    List<Course> getNext3Courses(int account_id, int amount);

    List<Course> getTop3Courses(int account_id);

    List<Course> getCoursesRegistration(int account_id);
}
