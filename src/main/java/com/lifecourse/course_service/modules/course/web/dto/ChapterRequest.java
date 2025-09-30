package com.lifecourse.course_service.modules.course.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChapterRequest(
        @NotBlank String title,
        String contentDescription,
        @Min(0) Integer orderIndex,
        @NotNull Long courseId,
        Long fileId,
        String contentUrl
) {}
