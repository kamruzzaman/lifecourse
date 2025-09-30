package com.lifecourse.course_service.modules.order.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(@NotBlank String username,
                               @NotNull Long courseId) {
}
