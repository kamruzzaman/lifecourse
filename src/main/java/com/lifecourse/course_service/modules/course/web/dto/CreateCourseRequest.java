package com.lifecourse.course_service.modules.course.web.dto;

import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseLevel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateCourseRequest(
        @NotBlank
        @Size(max = 255)
        String title,

        @Size(max = 65535)
        String description,

        @Size(max = 100)
        String category,

        @NotNull
        CourseLevel level,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        @Size(max = 500)
        String thumbnailUrl,

        @NotNull
        Long instructorId,

        @Min(0)
        Integer durationMinutes,

        Boolean published
) {}
