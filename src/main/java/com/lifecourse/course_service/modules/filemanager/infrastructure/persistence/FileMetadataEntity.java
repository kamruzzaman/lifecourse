package com.lifecourse.course_service.modules.filemanager.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FileMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "file_id_generator")
    @TableGenerator(
            name = "file_id_generator",
            table = "id_generator_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "file_id",
            allocationSize = 1
    )
    private long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 100)
    private FileType fileType=FileType.VEDIO;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_url", nullable = false, length = 1000)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", length = 20)
    private StorageType storageType = StorageType.LOCAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", length = 100)
    private ReferenceType referenceType;// e.g., CHAPTER, COURSE, USER_AVATAR

    @Column(name = "reference_id")
    private Long referenceId;

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
