package com.lifecourse.course_service.modules.course.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lifecourse.course_service.application.config.BaseEntity;
import com.lifecourse.course_service.modules.course.web.dto.Category;
import com.lifecourse.course_service.modules.course.web.dto.CourseLevel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Data
@Accessors(chain = true)  // ✅ Placed after @Data
@Entity
@Table(name = "courses",
        indexes = @Index(name = "idx_course_title", columnList = "title"))
public class CourseEntity extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") // MySQL-specific for long text
    private String description;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "course_categories", joinColumns = @JoinColumn(name = "course_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<ChapterEntity> chapters = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CourseLevel level = CourseLevel.BEGINNER;

    private BigDecimal price = BigDecimal.ZERO;



    @Column(name = "duration_minutes")
    private Integer durationMinutes = 0;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean published = false;




}
