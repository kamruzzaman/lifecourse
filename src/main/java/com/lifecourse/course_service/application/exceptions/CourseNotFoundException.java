package com.lifecourse.course_service.application.exceptions;


public class CourseNotFoundException   extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
