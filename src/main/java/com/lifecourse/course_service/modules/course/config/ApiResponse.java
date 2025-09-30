package com.lifecourse.course_service.modules.course.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ApiResponse {
    private Integer status;
    private String message;
    private Object data;

    public ApiResponse(Integer status, String message, Object data) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
