package com.lifecourse.course_service.modules.course.web.dto;

import java.util.List;

public record CourseResponse(
        Long id,
        String title,
        String description,
        String category,
        String level,
        String thumbnailUrl,
        List<ChapterResponse> chapters,
        List<InstructorResponse> instructors
) { }
