package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.lifecourse.course_service.modules.course.web.dto.ChapterRequest;
import com.lifecourse.course_service.modules.course.web.dto.ChapterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ChapterRepositoryAdapter {

    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;
    AtomicReference<ChapterResponse> chapterEntityAtomicReference=new AtomicReference<>();

    @Autowired
    public ChapterRepositoryAdapter(CourseRepository courseRepository, ChapterRepository chapterRepository) {
        this.courseRepository = courseRepository;
        this.chapterRepository = chapterRepository;
    }

    public Page<ChapterEntity> findAll(Pageable pageable) {
            return chapterRepository.findAll(pageable);
    }

    public Optional<ChapterEntity> findById(Long id) {
            return chapterRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return chapterRepository.existsById(id);
    }

    public void deleteById(Long id) {
        chapterRepository.deleteById(id);
    }

    public ChapterResponse createChapter(ChapterRequest request) {
        courseRepository.findById(request.courseId()).ifPresent(courseEntity -> {
            ChapterEntity chapter=new ChapterEntity()
                    .setTitle(request.title())
                    .setCourse(courseEntity)
                    .setContentDescription(request.contentDescription())
                    .setOrderIndex(request.orderIndex());

            chapterRepository.save(chapter);

            ChapterResponse chapterResponse = new ChapterResponse(
                    chapter.getId(),
                    chapter.getTitle(),
                    chapter.getContentDescription(),
                    chapter.getOrderIndex()
            );
            chapterEntityAtomicReference.set(chapterResponse);

        });
       if(chapterEntityAtomicReference.get()!=null) {
           return chapterEntityAtomicReference.get();
       }
       else{
           return null;
       }
    }

    public ChapterResponse editChapter(Long id, ChapterRequest request) {
        chapterRepository.findById(id).ifPresent(chapterEntity -> {
            courseRepository.findById(request.courseId()).ifPresent(courseEntity -> {
                chapterEntity.setTitle(request.title())
                        .setCourse(courseEntity)
                        .setContentDescription(request.contentDescription())
                        .setOrderIndex(request.orderIndex());
                chapterRepository.save(chapterEntity);
                ChapterResponse chapterResponse = new ChapterResponse(
                        chapterEntity.getId(),
                        chapterEntity.getTitle(),
                        chapterEntity.getContentDescription(),
                        chapterEntity.getOrderIndex()
                );
                chapterEntityAtomicReference.set(chapterResponse);
            });

        });
        if(chapterEntityAtomicReference.get()!=null) {
            return chapterEntityAtomicReference.get();
        }
        else{
            return null;
        }
    }

    public Page<ChapterEntity> findByCourseId(Long courseId,Pageable page) {
        return chapterRepository.findByCourseId(courseId,page);
    }
}
