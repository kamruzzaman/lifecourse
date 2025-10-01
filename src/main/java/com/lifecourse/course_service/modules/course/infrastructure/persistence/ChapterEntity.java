package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "chapters",
        indexes = @Index(name = "idx_chapter_course_id", columnList = "course_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class ChapterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "chapter_id_generator")
    @TableGenerator(
            name = "chapter_id_generator",
            table = "id_generator_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "chapter_id",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contentDescription;

    private Integer orderIndex;  // For ordering chapters within a course

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private CourseEntity course;

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
