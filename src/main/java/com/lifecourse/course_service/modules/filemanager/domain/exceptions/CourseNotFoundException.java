package com.lifecourse.course_service.modules.filemanager.domain.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Long id) {
        super("Course not found with ID: " + id);
    }
}
