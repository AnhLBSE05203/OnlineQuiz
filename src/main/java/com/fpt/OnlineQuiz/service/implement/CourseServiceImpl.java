package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CourseRepository;
import com.fpt.OnlineQuiz.dto.CourseFeaturedDTO;
import com.fpt.OnlineQuiz.dto.CourseUserDTO;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.service.CourseService;
import com.fpt.OnlineQuiz.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
