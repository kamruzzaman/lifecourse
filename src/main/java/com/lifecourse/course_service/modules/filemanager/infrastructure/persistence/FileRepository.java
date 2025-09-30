package com.lifecourse.course_service.modules.filemanager.infrastructure.persistence;

import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileMetadataEntity, Long> {
}
