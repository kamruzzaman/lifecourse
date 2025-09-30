package com.lifecourse.course_service.modules.filemanager.application;

import com.lifecourse.course_service.modules.filemanager.domain.File;
import com.lifecourse.course_service.modules.filemanager.domain.FileValidation;
import com.lifecourse.course_service.modules.filemanager.infrastructure.persistence.FileMetadataEntity;
import com.lifecourse.course_service.modules.filemanager.infrastructure.persistence.ReferenceType;
import com.lifecourse.course_service.modules.filemanager.infrastructure.persistence.StorageType;
import com.lifecourse.course_service.modules.filemanager.web.dto.FileResponse;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ChunkedFileService  {

    private static final String UPLOAD_DIR = "/var/www/html/uploaded_songs/";
    private static final ConcurrentHashMap<String, AtomicInteger> chunkCounter = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> totalChunksMap = new ConcurrentHashMap<>();

    @Async
    @Transactional
    public CompletableFuture<FileResponse> uploadChunkAsync(
            MultipartFile file,
            int chunkNumber,
            int totalChunks,
            Long refId,
            ReferenceType referenceType) {

        try {
            File filesUtil = new File();
            FileValidation fileValidation = new FileValidation();

            // Sanitize and get file extension
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                return CompletableFuture.failedFuture(new RuntimeException("Original filename is missing"));
            }

            String sanitizedFileName = filesUtil.sanitizeFileName(originalFilename);
            String fileExt = filesUtil.getFileExtension(sanitizedFileName);
            if (fileExt == null || fileExt.isBlank()) {
                return CompletableFuture.failedFuture(new RuntimeException("Invalid or missing file extension"));
            }

            // Generate unique ID and file name
            String fileUniqueId = UUID.randomUUID().toString().substring(0, 8);
            String fileName = fileUniqueId + "." + fileExt;

            Path uploadDirPath = Paths.get(filesUtil.getUploadDir(fileUniqueId));
            Path chunkPath = uploadDirPath.resolve(fileName + "." + chunkNumber);

            // Check disk space and folder availability
            if (!fileValidation.checkDiskSpaceAndFolder(uploadDirPath.toString(), file.getSize())) {
                return CompletableFuture.failedFuture(new RuntimeException("Insufficient disk space or folder error"));
            }

            // Write chunk to disk using buffered streams
            try (InputStream inputStream = file.getInputStream();
                 OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(chunkPath))) {

                byte[] buffer = new byte[8192];  // 8KB buffer
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Track uploaded chunks
            chunkCounter.putIfAbsent(fileName, new AtomicInteger(0));
            totalChunksMap.putIfAbsent(fileName, totalChunks);
            int uploadedChunks = chunkCounter.get(fileName).incrementAndGet();

            if (uploadedChunks == totalChunks) {
                filesUtil.mergeChunks(fileName, totalChunks, fileUniqueId);

                chunkCounter.remove(fileName);
                totalChunksMap.remove(fileName);

                // Clean up temp chunk folder
                Path tempDir = Paths.get(filesUtil.getUploadDir(fileUniqueId));
                if (Files.exists(tempDir)) {
                    Files.deleteIfExists(tempDir);
                }

                // Save metadata (assuming setters return `this` for chaining)
                FileMetadataEntity metadata = new FileMetadataEntity()
                        .setFileName(fileName)
                        .setStorageType(StorageType.LOCAL)
                        .setReferenceId(refId)
                        .setFileUrl(UPLOAD_DIR + fileName)
                        .setReferenceType(referenceType)
                        .setFileSize(filesUtil.getFileSizeInBytes(UPLOAD_DIR + fileName));


                return CompletableFuture.completedFuture(
                        new FileResponse(UPLOAD_DIR + fileName, "File Uploaded Successfully"));
            }

            return CompletableFuture.completedFuture(new FileResponse("", "File Chunk Uploaded Successfully"));

        } catch (IOException e) {
            return CompletableFuture.failedFuture(new RuntimeException("Error uploading file chunk", e));
        }
    }


}
