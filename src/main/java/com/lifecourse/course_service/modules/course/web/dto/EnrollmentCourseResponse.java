package com.lifecourse.course_service.modules.course.web.dto;

import java.time.LocalDateTime;

public record EnrollmentCourseResponse(
        String userExternalPublicId,
        String coursePublicId,
        Integer attemptNumber,
        LocalDateTime enrollmentDateTime,
        EnrollmentStatus status,
        Grade grade
) {}
