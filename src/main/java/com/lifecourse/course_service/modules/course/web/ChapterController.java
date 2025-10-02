package com.lifecourse.course_service.modules.course.web;

import com.lifecourse.course_service.modules.course.application.ChapterService;
import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.utils.ResponseUtil;
import com.lifecourse.course_service.modules.course.web.dto.ChapterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/chapters")
@Validated
public class ChapterController {

    private final ChapterService chapterService;
    private final MessageSource messageSource;
    @Autowired
    public ChapterController(ChapterService chapterService, MessageSource messageSource) {
        this.chapterService = chapterService;
        this.messageSource = messageSource;
    }

    @GetMapping( produces = "application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAll(Pageable pageable,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                chapterService.getAllChapters(pageable),
                messageSource,
                "get.success",
                locale
        );
    }

    @GetMapping("course/{courseId}")
    public ResponseEntity<ApiResponse> getAllByCourseId(@PathVariable Long courseId,Pageable pageable,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                chapterService.getAllByCourseId(courseId,pageable),
                messageSource,
                "get.success",
                locale
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id, Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                chapterService.getChapterById(id),
                messageSource,
                "get.success",
                locale
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ChapterRequest request,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.POST,
                chapterService.createChapter(request),
                messageSource,
                "get.success",
                locale
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody ChapterRequest request,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.PUT,
                chapterService.updateChapter(id, request),
                messageSource,
                "get.success",
                locale
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.DELETE,
                chapterService.deleteChapter(id),
                messageSource,
                "get.success",
                locale
        );
    }
}
