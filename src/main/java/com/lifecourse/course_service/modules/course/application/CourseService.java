package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.application.util.SecurityUtils;
import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseRepositoryAdapter;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.EnrollmentRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.lifecourse.course_service.modules.course.utils.DataPageUtil.convertToDatapage;

@Service
public class CourseService  {

    private final CourseRepositoryAdapter course;
    private final EnrollmentRepositoryAdapter enrollmentRepositoryAdapter;
    public CourseService(CourseRepositoryAdapter course, EnrollmentRepositoryAdapter enrollmentRepositoryAdapter) {
        this.course = course;
        this.enrollmentRepositoryAdapter = enrollmentRepositoryAdapter;
    }
    public DataPage getAllCourses(Pageable pageable) {
        return convertToDatapage(course.findAll(pageable));
    }
    public DataPage getAllCoursesByUser(String username, Pageable pageable) {
         return convertToDatapage(course.findAllByUserName(username,pageable));
    }
    public Optional<CourseEntity> getCourseById(String publicId) {
        return course.findByPublicId(publicId);
    }
    public  CourseResponse createCourse(CreateCourseRequest createCourseRequest) {
       return course.createCourse(createCourseRequest);
    }
    public CourseResponse updateCourse(String publicId, CreateCourseRequest createCourseRequest) {
       try {
           return course.editCourse(publicId, createCourseRequest);
       }
       catch (EntityNotFoundException ex){
           return null;
       }
    }
    public String deleteCourse(String publicId) {
        try {
            if(isEligibleForDeletion(publicId) && isAdminOrCreatedByCurrentUser(publicId)) {
                course.delete(publicId);
                return "delete.success"; //message key
            }
            else{
                return null;
            }
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private boolean isAdminOrCreatedByCurrentUser(String publicId) {
        if(SecurityUtils.getCurrentUser().roles().contains("Admin")) {
          return true;
        }
        try {
            course.findByPublicIdAndCreatedBy(publicId, SecurityUtils.getCurrentUser().username());
            return true;
        }
        catch (EntityNotFoundException ex){
            return false;
        }
    }

    private boolean isEligibleForDeletion(String publicId) {
       return !enrollmentRepositoryAdapter.checkCourseHaveStudent(publicId);
    }



}
