package com.lifecourse.course_service.modules.filemanager.web;

import com.lifecourse.course_service.modules.filemanager.application.ChunkedFileService;
import com.lifecourse.course_service.modules.filemanager.config.ApiResponse;
import com.lifecourse.course_service.modules.filemanager.infrastructure.persistence.ReferenceType;
import com.lifecourse.course_service.modules.filemanager.web.dto.FileResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/v1/files")
public class AsyncChunkedFileController {

    private final ChunkedFileService fileService;
    private static final Logger log = LoggerFactory.getLogger(AsyncChunkedFileController.class);
    @Autowired
    public AsyncChunkedFileController(ChunkedFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadChunk")
    public CompletableFuture<ResponseEntity<ApiResponse<FileResponse>>>  uploadChunk(
            @RequestParam(value = "file", required = true)
            @NotNull(message = "File must not be null!") MultipartFile file,

            @RequestParam(value = "chunkNumber", required = true)
            @NotNull(message = "Chunk number cannot be null!") Integer chunkNumber,

            @RequestParam(value = "totalChunks", required = true)
            @NotNull(message = "Total chunks cannot be null!") Integer totalChunks,

            @RequestParam(value = "referenceId", required = true)
            @NotNull(message = "Reference ID cannot be null!") Long referenceId,

            @RequestParam(value = "referenceType", required = true)
            @NotNull(message = "Reference type must not be null!") ReferenceType referenceType
    ){
        log.info("Uploading chunk {}/{} for file: {}", totalChunks, chunkNumber);
        return fileService.uploadChunkAsync( file,chunkNumber,totalChunks,referenceId,referenceType)
                .thenApply(response -> {
                    log.info("Chunk {}/{} uploaded successfully for file: {}", chunkNumber, totalChunks, file.getOriginalFilename());
                    return ResponseEntity.ok(ApiResponse.success("Chunk uploaded successfully", response));
                })
                .exceptionally(ex -> {
                    log.error("Failed to upload chunk {}/{} for file: {}", chunkNumber, totalChunks, file.getOriginalFilename(), ex);
                    return ResponseEntity.badRequest().body(ApiResponse.error(ex.getLocalizedMessage()));
                });
    }
}
