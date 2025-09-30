package com.lifecourse.course_service.modules.course.web;

import com.lifecourse.course_service.modules.course.application.ChapterService;
import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.web.dto.ChapterRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chapters")
@Validated
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok(chapterService.getAllChapters(pageable));
    }

    @GetMapping("course/{courseId}")
    public ResponseEntity<ApiResponse> getAllByCourseId(@PathVariable Long courseId,Pageable pageable) {
        return ResponseEntity.ok(chapterService.getAllByCourseId(courseId,pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ChapterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chapterService.createChapter(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody ChapterRequest request) {
        return ResponseEntity.ok( chapterService.updateChapter(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}
