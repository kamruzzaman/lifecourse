package com.lifecourse.course_service.modules.course.web.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record CreateCourseRequest(
        @NotBlank
        @Size(max = 255)
        String title,

        @Size(max = 600)
        String description,

        Set<Category> category,

        @NotNull
        CourseLevel level,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        @Digits(integer = 8, fraction = 2)
        BigDecimal price,

        List<Long> userExternalId,

        @Min(0)
        Integer durationMinutes,

        Boolean published
) {}
