package com.lifecourse.course_service.modules.course.web.dto;

import java.util.List;
import java.util.Set;

public record CourseResponse(
        Long id,
        String title,
        String description,
        Set<Category> category,
        String level,
        List<ChapterResponse> chapters,
        List<InstructorResponse> instructors
) { }
