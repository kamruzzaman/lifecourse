package com.lifecourse.course_service.modules.course.web.dto;

import java.util.List;

public record CourseProgressResponse(
        Long courseId,
        String courseTitle,
        int totalChapters,
        int completedChapters,
        double completionPercentage,
        List<ChapterResponse> chapterProgress
) {}
