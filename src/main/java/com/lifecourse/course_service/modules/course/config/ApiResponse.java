package com.lifecourse.course_service.modules.course.config;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;

    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
