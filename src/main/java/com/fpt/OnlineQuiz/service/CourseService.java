package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;

import java.util.List;

public interface CourseService {
    public List<CourseFeaturedDTO> getFeaturedCourses(int number);

    List<Course> getNext3Courses(int accountId, int start);

    List<Course> getTop3Courses(int accountId);

    List<Course> getCoursesRegistration(int accountId);

    CourseUserDTO getCourseUserDTO(int id);

    Page<CourseAdminDTO> getCourseAdminDTOByPagingRequest(int subjectId, PagingRequest pagingRequest);

    Course getById(int id);

    void updateCourse(Course course);

    CourseAdminDTO getCourseAdminDTOById(int id);

    boolean isDuplicated(String name, int subjectId);

}
