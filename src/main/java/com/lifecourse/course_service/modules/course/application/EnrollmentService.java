package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.application.exceptions.CourseNotFoundException;
import com.lifecourse.course_service.application.util.SecurityUtils;
import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseRepositoryAdapter;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.EnrollmentRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentCourseRequest;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentCourseResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.lifecourse.course_service.modules.course.utils.DataPageUtil.convertToDatapage;

@Service
public class EnrollmentService {

    private final CourseRepositoryAdapter courseAdapter;
    private final EnrollmentRepositoryAdapter enrollmentRepositoryAdapter;
    public EnrollmentService(CourseRepositoryAdapter course, EnrollmentRepositoryAdapter enrollmentRepositoryAdapter) {
        this.courseAdapter = course;
        this.enrollmentRepositoryAdapter = enrollmentRepositoryAdapter;
    }
    public DataPage getAllCourses(Pageable pageable) {
        return convertToDatapage(enrollmentRepositoryAdapter.findAll(pageable));
    }
    public DataPage getAllCoursesByUser(String username, Pageable pageable) {
         return convertToDatapage(courseAdapter.findAllByUserName(username,pageable));
    }
    public Optional<CourseEntity> getCourseById(String publicId) {
        return courseAdapter.findByPublicId(publicId);
    }

    public EnrollmentCourseResponse createCourse(EnrollmentCourseRequest enrollmentCourseRequest) {
        String userId = enrollmentCourseRequest.userExternalPublicId();
        String coursePublicId = enrollmentCourseRequest.coursePublicId();
        CourseEntity courseEntity = courseAdapter.findByPublicId(coursePublicId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found for publicId: " + coursePublicId));

        // ✅ If already enrolled, stop early
        if (isUserAlreadyEnrolled( courseEntity.getId(),userId)) {
            throw new CourseNotFoundException("User is already enrolled in this course");
        }

        // ✅ Add user to course and return response
        return enrollmentRepositoryAdapter.addUserToCourse(enrollmentCourseRequest, courseEntity);
    }
    public CourseResponse updateCourse(String publicId, CreateCourseRequest createCourseRequest) {
       try {
           return courseAdapter.editCourse(publicId, createCourseRequest);
       }
       catch (EntityNotFoundException ex){
           return null;
       }
    }
    public String deleteCourse(String publicId) {
        try {
            if(isEligibleForDeletion(publicId) && isAdminOrCreatedByCurrentUser(publicId)) {
                courseAdapter.delete(publicId);
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
            courseAdapter.findByPublicIdAndCreatedBy(publicId, SecurityUtils.getCurrentUser().username());
            return true;
        }
        catch (EntityNotFoundException ex){
            return false;
        }
    }

    private boolean isEligibleForDeletion(String publicId) {
      return false;
        // return !enrollmentRepositoryAdapter.checkCourseHaveStudent(publicId);
    }

    private boolean isUserAlreadyEnrolled(Long courseId,String userExternalId) {
        return enrollmentRepositoryAdapter.checkCourseHaveStudentAndStatusDrop(courseId,userExternalId);
    }

}
