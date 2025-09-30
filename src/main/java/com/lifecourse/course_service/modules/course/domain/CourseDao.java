package com.lifecourse.course_service.modules.course.domain;

public record CourseDao(
        Long id,
        String title,
        boolean published
) {}
