package com.lifecourse.course_service.modules.course.web.dto;

public record ChapterResponse(
        Long id,
        String title,
        String description,
        Integer orderIndex,
        String resourceUrl
) {}
