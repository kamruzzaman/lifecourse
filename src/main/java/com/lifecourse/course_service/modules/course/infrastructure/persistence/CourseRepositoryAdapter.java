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


    public CourseResponse createCourse(CreateCourseRequest request) {
        CourseEntity course = new CourseEntity()
                .setTitle(request.title())
                .setDescription(request.description())
                .setCategory(request.category())
                .setLevel(request.level())
                .setPrice(request.price())
                .setThumbnailUrl(request.thumbnailUrl())
                .setInstructorId(request.instructorId())
                .setDurationMinutes(request.durationMinutes())
                .setPublished(request.published() != null && request.published());

        CourseEntity savedCourseEntity=courseRepository.save(course);
        return  new CourseResponse(
                savedCourseEntity.getId(),
                savedCourseEntity.getTitle(),
                savedCourseEntity.getDescription(),
                savedCourseEntity.getCategory(),
                savedCourseEntity.getLevel().name(), // assuming enum
                savedCourseEntity.getThumbnailUrl(),
                Collections.emptyList(),      // List<ChapterResponse>
                Collections.emptyList() // List<InstructorResponse>
        );

    }

    public CourseResponse editCourse(Long id, CreateCourseRequest request) {
        courseRepository.findById(id).ifPresent(course -> {
            course.setTitle(request.title())
                    .setDescription(request.description())
                    .setCategory(request.category())
                    .setLevel(request.level())
                    .setPrice(request.price())
                    .setThumbnailUrl(request.thumbnailUrl())
                    .setInstructorId(request.instructorId())
                    .setDurationMinutes(request.durationMinutes())
                    .setPublished(request.published() != null && request.published());

            CourseEntity savedCourseEntity= courseRepository.save(course);
            courseEntityAtomicReference.set(savedCourseEntity);
        });
        if(courseEntityAtomicReference.get().getId()!=null) {
            return new CourseResponse(
                    courseEntityAtomicReference.get().getId(),
                    courseEntityAtomicReference.get().getTitle(),
                    courseEntityAtomicReference.get().getDescription(),
                    courseEntityAtomicReference.get().getCategory(),
                    courseEntityAtomicReference.get().getLevel().name(), // assuming enum
                    courseEntityAtomicReference.get().getThumbnailUrl(),
                    Collections.emptyList(),      // List<ChapterResponse>
                    Collections.emptyList() // List<InstructorResponse>
            );
        }
        else{
            return null;
        }
    }
}
