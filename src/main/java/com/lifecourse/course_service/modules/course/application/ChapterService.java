package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.domain.ChapterBR;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.ChapterEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.ChapterRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.ChapterRequest;
import com.lifecourse.course_service.modules.course.web.dto.ChapterResponse;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ChapterService {
    private final ChapterRepositoryAdapter chapterRepository;

    public ChapterService(ChapterRepositoryAdapter chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public ApiResponse getAllChapters(Pageable pageable) {
        ChapterBR chapterBR = new ChapterBR();
        Page<ChapterEntity> page = chapterRepository.findAll(pageable);
        return chapterBR.getAllChapters(page);
    }

    public ApiResponse getChapterById(Long id) {
       Optional<ChapterEntity> chapter= chapterRepository.findById(id);
       if(!chapter.isEmpty()){
           return new ApiResponse(200, "Chapter  Found", chapter.get());
       }
       return  new ApiResponse(200, "Chapter Not Found", null);

    }

    public ApiResponse createChapter(ChapterRequest request) {
        ChapterResponse chapterResponse = chapterRepository.createChapter(request);
        return new ApiResponse(201, "Chapter created", chapterResponse);
    }

    public ApiResponse updateChapter(Long id, ChapterRequest request) {
        ChapterResponse chapterResponse = chapterRepository.editChapter(id, request);
        if (ObjectUtils.isEmpty(chapterResponse)) {
            return new ApiResponse(400, "Failed to update chapter", null);
        }
        return new ApiResponse(200, "Chapter updated", chapterResponse);
    }

    public ApiResponse deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            return new ApiResponse(200, "Chapter not found", null);
        }
        chapterRepository.deleteById(id);
        return new ApiResponse(200, "Chapter deleted successfully", Collections.emptyList());
    }

    public ApiResponse getAllByCourseId(Long courseId,Pageable pageable) {
         ChapterBR chapterBR=new ChapterBR();
         return  chapterBR.getAllCourseId(chapterRepository.findByCourseId(courseId,pageable));
    }
}
