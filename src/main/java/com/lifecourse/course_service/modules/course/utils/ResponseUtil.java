package com.lifecourse.course_service.modules.course.utils;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public final class ResponseUtil {

    private ResponseUtil() {}
    public static <T> ResponseEntity<ApiResponse> buildResponse(HttpMethod method,  T object, MessageSource messageSource,String msgKey, Locale locale) {
        return switch (method.name()) {
            case "GET" -> {
                if (!ObjectUtils.isEmpty(object)) {
                    yield build(HttpStatus.OK, messageSource.getMessage(msgKey,null,locale), object);
                } else {
                    yield build(HttpStatus.NOT_FOUND,  messageSource.getMessage("get.notfound", null, locale), null);
                }
            }
            case "POST" -> {
                if (!ObjectUtils.isEmpty(object)) {
                    yield build(HttpStatus.CREATED, messageSource.getMessage(msgKey,null,locale), object);
                } else {
                    yield build(HttpStatus.BAD_REQUEST,  messageSource.getMessage("post.validation", null, locale), null);
                }
            }
            case "DELETE" -> {
                if (!ObjectUtils.isEmpty(object)) {
                    yield build(HttpStatus.NO_CONTENT, "DELETE successful", null);
                } else {
                    yield build(HttpStatus.NOT_FOUND, messageSource.getMessage("get.notfound", null, locale), null);
                }
            }
            case "PUT" -> {
                if (!ObjectUtils.isEmpty(object)) {
                    yield build(HttpStatus.OK, messageSource.getMessage(msgKey,null,locale), object);
                } else {
                    yield build(HttpStatus.BAD_REQUEST,  messageSource.getMessage("put.validation", null, locale), null);
                }
            }
            case "PATCH" -> {
                if (!ObjectUtils.isEmpty(object)) {
                    yield build(HttpStatus.OK, messageSource.getMessage(msgKey,null,locale), object);
                } else {
                    yield build(HttpStatus.BAD_REQUEST,  messageSource.getMessage("post.validation", null, locale), null);
                }
            }
            default -> build(HttpStatus.INTERNAL_SERVER_ERROR,   messageSource.getMessage("error.unexpected", null, locale), null);
        };
    }

    // Helper method to build ResponseEntity<ApiResponse>
    private static ResponseEntity<ApiResponse> build(HttpStatus status, String message, Object body) {
        ApiResponse response = new ApiResponse(status, message, body);
        return ResponseEntity.status(status).body(response);
    }
}
