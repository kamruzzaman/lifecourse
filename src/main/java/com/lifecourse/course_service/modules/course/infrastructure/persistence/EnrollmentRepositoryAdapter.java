package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentCourseRequest;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentCourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class EnrollmentRepositoryAdapter {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentRepositoryAdapter(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    public boolean checkCourseHaveStudent(Long courseId){
       return enrollmentRepository.existsById_CourseId(courseId);
    }
    public boolean checkCourseHaveStudentAndStatusDrop(Long courseId,String userPublicId){
        return !enrollmentRepository.existsEnrollment(EnrollmentStatus.DROPPED,courseId,userPublicId);
    }
    private EnrollmentCourseResponse mapToEnrollmentResponse(CourseEnrollment courseEnrollment,String userPublicId) {
        return new EnrollmentCourseResponse(
                courseEnrollment.getId().getUserExternalId(),
                userPublicId,
                courseEnrollment.getId().getAttemptNumber(),
                courseEnrollment.getEnrollmentDate(),
                courseEnrollment.getStatus(),
                courseEnrollment.getGrade()

        );
    }
    public EnrollmentCourseResponse addUserToCourse(EnrollmentCourseRequest enrollmentCourseRequest,CourseEntity courseEntity) {
        CourseEnrollmentId enrollmentId = new CourseEnrollmentId(
                enrollmentCourseRequest.userExternalPublicId(),
                courseEntity.getId(),
                1
        );
        CourseEnrollment courseEnrollment = new CourseEnrollment();
        courseEnrollment.setId(enrollmentId);

        return  mapToEnrollmentResponse(enrollmentRepository.save(courseEnrollment),enrollmentCourseRequest.userExternalPublicId());
    }

    public Page<CourseEnrollment> findAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable);
    }
}
