package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import org.springframework.stereotype.Component;

@Component
public class EnrollmentRepositoryAdapter {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentRepositoryAdapter(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    public boolean checkCourseHaveStudent(String publicId){
       return enrollmentRepository.existsByCoursePublicId(publicId);
    }
}
