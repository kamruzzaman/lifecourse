package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.lifecourse.course_service.modules.course.web.dto.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    @Query("SELECT CASE WHEN COUNT(ce) > 0 THEN true ELSE false END FROM CourseEnrollment ce WHERE ce.status = :status AND ce.id.courseId = :courseId AND ce.id.userExternalId = :userExternalId AND ce.id.attemptNumber =1")
    boolean existsEnrollment(@Param("status") EnrollmentStatus status,
                             @Param("courseId") Long courseId,
                             @Param("userExternalId") String userExternalId);
    boolean existsById_CourseId(Long courseId);
}
