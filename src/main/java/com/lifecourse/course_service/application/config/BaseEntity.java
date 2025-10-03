package com.lifecourse.course_service.application.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners({SnowflakeEntityListener.class,AuditingEntityListener.class})
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @JsonIgnore
    private Long Id;
    @Column(nullable = false, unique = true, updatable = false, length = 20)
    private String publicId;

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
