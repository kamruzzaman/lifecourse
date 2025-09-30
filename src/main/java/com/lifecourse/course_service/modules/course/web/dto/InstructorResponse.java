package com.lifecourse.course_service.modules.course.web.dto;

public record InstructorResponse(
        Long id,
        String fullName,
        String email,
        String bio,
        String profileImageUrl

) { }
