package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {
    Page<ChapterEntity> findByCourseId(Long courseId, Pageable page);
}
