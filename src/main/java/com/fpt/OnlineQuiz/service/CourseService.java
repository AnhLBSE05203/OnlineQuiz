package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    public List<CourseFeaturedDTO> getFeaturedCourses(int number);

    List<Course> getNext3Courses(int accountId, int start);

    List<Course> getTop3Courses(int accountId);

    List<Course> getCoursesRegistration(int accountId);

    CourseUserDTO getCourseUserDTO(int id);

    Page<CourseAdminDTO> getCourseAdminDTOByPagingRequest(int subjectId, PagingRequest pagingRequest);

    Course getById(int id);

    void updateCourse(Course course);

    void addCourse(Course course);

    CourseAdminDTO getCourseAdminDTOById(int id);

    boolean isDuplicated(String name, int subjectId);


    List<Course> getCoursesTop3BySubjectId(int subjectId);

    List<Course> getCoursesNext3BySubjectId(int subjectId, int start);

    Optional<Course> getCourse(int id);

}
