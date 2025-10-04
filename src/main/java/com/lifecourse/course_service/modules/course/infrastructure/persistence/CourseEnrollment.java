package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lifecourse.course_service.modules.course.web.dto.EnrollmentStatus;
import com.lifecourse.course_service.modules.course.web.dto.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {

    @EmbeddedId
    private CourseEnrollmentId id;

    @Column(name = "enrollment_date", updatable = false)
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING) // store enum name as text
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    @Column(nullable = false)
    private Double progress = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade = Grade.NOT_GRADED;

}
