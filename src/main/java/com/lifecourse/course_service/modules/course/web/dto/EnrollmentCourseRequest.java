package com.lifecourse.course_service.modules.course.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnrollmentCourseRequest(
        @NotBlank
        @Size(max = 20)
        String userExternalPublicId,

        @NotBlank
        @Size(max = 20)
        String coursePublicId,
        @Min(1)
        Integer attemptNumber,
        EnrollmentStatus status,
        Grade grade,
        Boolean published
) {}
