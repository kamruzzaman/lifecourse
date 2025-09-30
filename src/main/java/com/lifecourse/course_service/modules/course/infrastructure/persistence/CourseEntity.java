package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Accessors(chain = true)  // ✅ Placed after @Data
@Entity
@Table(name = "courses")
@EntityListeners(AuditingEntityListener.class)
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "course_id_generator")
    @TableGenerator(
            name = "course_id_generator",
            table = "id_generator_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "course_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // MySQL-specific for long text
    private String description;


    private String category;

    @Enumerated(EnumType.STRING)
    private CourseLevel level = CourseLevel.BEGINNER;

    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    @Column(name = "duration_minutes")
    private Integer durationMinutes = 0;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean published = false;

    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdAt;

    @CreatedBy
    @Column(name = "createdBy", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updatedAt", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant updatedAt;

    @LastModifiedBy
    @Column(name = "updatedBy", nullable = true)
    private String updatedBy;


}
