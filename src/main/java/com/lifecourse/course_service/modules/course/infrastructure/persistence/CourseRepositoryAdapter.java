package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
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
    AtomicBoolean isSaved = new AtomicBoolean(false);
    AtomicReference<CourseEntity> courseEntityAtomicReference = new AtomicReference<CourseEntity>();
    public CourseRepositoryAdapter(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public Page<CourseEntity> findAllByUserName(String userName, Pageable pageable) {
        return courseRepository.findAllByCreatedBy(userName,pageable);
    }


    public Page<CourseEntity> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }


    public CourseEntity findById(Long id) {
        Optional<CourseEntity> resultEntity=courseRepository.findById(id);
         if(resultEntity.isPresent()){
             return resultEntity.get();
         }
         return null;

    }

    public CourseEntity save(CourseEntity course) {
        return courseRepository.save(course);

    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
    private CourseResponse mapToCourseResponse(CourseEntity course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategories(),
                course.getLevel().name(),
                Collections.emptyList(), // chapters
                Collections.emptyList()  // instructors
        );
    }

    public CourseResponse createCourse(CreateCourseRequest request) {
        CourseEntity course = CourseEntity.builder()
                .title(request.title())
                .description(request.description())
                .categories(request.category())
                .level(request.level())
                .price(request.price())
                .instructorId(request.instructorId())
                .durationMinutes(request.durationMinutes())
                .published(Boolean.TRUE.equals(request.published()))
                .build();

        CourseEntity savedCourse = courseRepository.save(course);
        return mapToCourseResponse(savedCourse);

    }

    public CourseResponse editCourse(Long id, CreateCourseRequest request) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(request.title())
                            .setDescription(request.description())
                            .setCategories(request.category())
                            .setLevel(request.level())
                            .setPrice(request.price())
                            .setInstructorId(request.instructorId())
                            .setDurationMinutes(request.durationMinutes())
                            .setPublished(Boolean.TRUE.equals(request.published()));

                   return  mapToCourseResponse( courseRepository.save(course));

                })
                .orElseThrow(() -> null);
    }
}
