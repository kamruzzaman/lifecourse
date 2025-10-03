package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    List<CourseEnrollment> findByIdUserExternalId(String userExternalId);
    boolean existsByCoursePublicId(String publicId);
    Optional<CourseEnrollment> findByCoursePublicId(String publicId);

}
