package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentId implements Serializable {
    @Column(name = "user_external_id")
    private String userExternalId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "attempt_number")
    private Integer attemptNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // same reference
        if (!(o instanceof CourseEnrollmentId)) return false; // not same type
        CourseEnrollmentId that = (CourseEnrollmentId) o;
        return Objects.equals(userExternalId, that.userExternalId) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(attemptNumber, that.attemptNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userExternalId, courseId, attemptNumber);
    }

}
