package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.lifecourse.course_service.modules.course.utils.DataPageUtil.convertToDatapage;

@Service
public class CourseService  {

    private final CourseRepositoryAdapter course;
    public CourseService(CourseRepositoryAdapter course) {
        this.course = course;
    }
    public DataPage getAllCourses(Pageable pageable) {
        return convertToDatapage(course.findAll(pageable));
    }
    public DataPage getAllCoursesByUser(String username, Pageable pageable) {
         return convertToDatapage(course.findAllByUserName(username,pageable));
    }
    public Optional<CourseEntity> getCourseById(Long id) {
        return course.findById(id);
    }
    public  CourseResponse createCourse(CreateCourseRequest createCourseRequest) {
       return course.createCourse(createCourseRequest);
    }
    public CourseResponse updateCourse(Long id, CreateCourseRequest createCourseRequest) {
        return course.editCourse(id,createCourseRequest);
    }
    public String deleteCourse(Long id) {
        try {
            course.delete(id);
            return "delete.success"; //message key
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

}
