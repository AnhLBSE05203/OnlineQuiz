package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CourseRepository;
import com.fpt.OnlineQuiz.dto.CourseAdminDTO;
import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseFeaturedDTO> getFeaturedCourses(int number) {
        List<CourseFeaturedDTO> courses = courseRepository.getFeaturedCourses(number);
        //trim description
        for (CourseFeaturedDTO course : courses) {
            String description = course.getDescription();
            if (description.length() > Constants.DESCRIPTION_TRIM_LENGTH) {
                description = description.substring(
                        Constants.NUMBER_ZERO,
                        Math.min(description.length(), Constants.DESCRIPTION_TRIM_LENGTH));
                description += Constants.DESCRIPTION_TRIM_TAIL;
                course.setDescription(description);
            }
        }
        return courses;
    }

    @Override
    public List<Course> getNext3Courses(int accountId, int start) {
        return courseRepository.getNext3Courses(accountId, start);
    }

    @Override
    public List<Course> getTop3Courses(int accountId) {
        return courseRepository.getTop3Courses(accountId);
    }


    @Override
    public List<Course> getCoursesRegistration(int accountId) {
        return courseRepository.getTop3Courses(accountId);
    }

    @Override
    public CourseUserDTO getCourseUserDTO(int id) {
        Course course = courseRepository.getCourseById(id);
        CourseUserDTO courseUserDTO = course.toCourseUserDTO();
        return courseUserDTO;
    }

    @Override
    public Page<CourseAdminDTO> getCourseAdminDTOByPagingRequest(int subjectId, PagingRequest pagingRequest) {
        List<Course> courses = courseRepository.getCoursesByPagingRequest(subjectId, pagingRequest);
        long count = courseRepository.getCoursesCountByPagingRequest(subjectId, pagingRequest);
        List<CourseAdminDTO> courseAdminDTOList = new ArrayList<>();
        for (Course course : courses) {
            CourseAdminDTO courseAdminDTO = course.toCourseAdminDTO();
            courseAdminDTOList.add(courseAdminDTO);
        }
        // convert List to Page
        Page<CourseAdminDTO> page = new Page<>(courseAdminDTOList);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());
        return page;
    }

    @Override
    public Course getById(int id) {
        return courseRepository.getCourseById(id);
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.updateCourse(course);
    }

    @Override
    public void addCourse(Course course) {
        courseRepository.addCourse(course);
    }

    @Override
    public CourseAdminDTO getCourseAdminDTOById(int id) {
        Course course = courseRepository.getCourseById(id);
        CourseAdminDTO courseAdminDTO = course.toCourseAdminDTO();
        return courseAdminDTO;
    }

    @Override
    public boolean isDuplicated(String name, int subjectId) {
        long count = courseRepository.getDuplicateCount(name, subjectId);
        return count > 0 ? true : false;
    }

    @Override
    public void addCourseRegistration(List<Course> list, int accountId) {
        courseRepository.addRegistrationCourse(list, accountId);
    }

    @Override
    public List<Course> getCoursesTop3BySubjectId(int subjectId) {
        return courseRepository.getTop3CoursesBySubjectId(subjectId);
    }
    @Override
    public List<Course> getCoursesNext3BySubjectId(int subjectId, int start) {
        return courseRepository.getNext3CoursesBySubjectId(subjectId, start);
    }
}
