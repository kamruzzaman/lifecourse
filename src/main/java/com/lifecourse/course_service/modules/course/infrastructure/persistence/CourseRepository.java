package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Page<CourseEntity> findAllByCreatedBy(String userName, Pageable pageable);
    Optional<CourseEntity> findByPublicId(String publicId);
    Optional<CourseEntity> findByPublicIdAndCreatedBy(String publicId, String createdBy);

    void deleteByPublicId(String publicId);

    boolean existsByPublicId(String publicId);
}
