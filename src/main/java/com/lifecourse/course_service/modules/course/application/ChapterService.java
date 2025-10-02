package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.domain.ChapterBR;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.ChapterEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.ChapterRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.ChapterRequest;
import com.lifecourse.course_service.modules.course.web.dto.ChapterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.lifecourse.course_service.modules.course.utils.DataPageUtil.convertToDatapage;

@Service
public class ChapterService {

    private final ChapterRepositoryAdapter chapterRepository;

    @Autowired
    public ChapterService(ChapterRepositoryAdapter chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public DataPage getAllChapters(Pageable pageable) {
         return convertToDatapage(chapterRepository.findAll(pageable));
    }

    public Optional<ChapterEntity>  getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public ChapterResponse createChapter(ChapterRequest request) {
        return  chapterRepository.createChapter(request);
    }

    public ChapterResponse updateChapter(Long id, ChapterRequest request) {
        return  chapterRepository.editChapter(id, request);
    }

    public String deleteChapter(Long id) {
        try {
            chapterRepository.deleteById(id);
            return "delete.success"; //message key
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public DataPage getAllByCourseId(Long courseId,Pageable pageable) {
         return  convertToDatapage(chapterRepository.findByCourseId(courseId,pageable));
    }
}
