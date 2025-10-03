package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.codec.Base64;
import com.lifecourse.course_service.application.util.SecurityUtils;
import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CourseRepositoryAdapter {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseRepositoryAdapter(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public Page<CourseEntity> findAllByUserName(String userName, Pageable pageable) {
        return courseRepository.findAllByCreatedBy(userName,pageable);
    }


    public Page<CourseEntity> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }


    public Optional<CourseEntity> findByPublicId(String publicId) {
       return  courseRepository.findByPublicId(publicId);
    }

    public CourseEntity save(CourseEntity course) {
        return courseRepository.save(course);
    }

    public void findByPublicIdAndCreatedBy(String publicId,String currentUserr) {
         courseRepository.findByPublicIdAndCreatedBy(publicId, currentUserr)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public void delete(String publicId) throws EntityNotFoundException {
        if (!courseRepository.existsByPublicId(publicId)) {
            throw new EntityNotFoundException("Course with id " + publicId + " not found");
        }
        courseRepository.deleteByPublicId(publicId);
    }
    private CourseResponse mapToCourseResponse(CourseEntity course) {
        return new CourseResponse(
                course.getPublicId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategories(),
                course.getLevel().name(),
                Collections.emptyList(), // chapters
                Collections.emptyList()  // instructors
        );
    }

    public CourseResponse createCourse(CreateCourseRequest request) {
        CourseEntity course = new CourseEntity()
                .setTitle(request.title())
                .setDescription(request.description())
                .setCategories(request.category())
                .setLevel(request.level())
                .setPrice(request.price())
                .setDurationMinutes(request.durationMinutes())
                .setPublished(Boolean.TRUE.equals(request.published()));
        return  mapToCourseResponse(courseRepository.save(course));


    }

    public CourseResponse editCourse(String publicId, CreateCourseRequest request) {
        String currentUser = SecurityUtils.getCurrentUser().username();

        CourseEntity course = courseRepository.findByPublicIdAndCreatedBy(publicId, currentUser)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course not found for publicId=" + publicId + " and user=" + currentUser));

        updateCourseEntity(course, request);

        CourseEntity updatedCourse = courseRepository.save(course);

        return mapToCourseResponse(updatedCourse);
    }

    private void updateCourseEntity(CourseEntity course, CreateCourseRequest request) {
        course.setTitle(request.title())
                .setDescription(request.description())
                .setCategories(request.category())
                .setLevel(request.level())
                .setPrice(request.price())
                .setDurationMinutes(request.durationMinutes())
                .setPublished(Boolean.TRUE.equals(request.published()));
    }
}
