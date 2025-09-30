package com.lifecourse.course_service.modules.filemanager.domain;

import lombok.NoArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class FileValidation {
    private static final Logger log = LoggerFactory.getLogger(FileValidation.class);

    private static final long MIN_REQUIRED_SPACE = 50 * 1024 * 1024; // 50 MB minimum space required
    private  final Tika tika = new Tika();

    private  final Set<String> ALLOWED_VIDEO_MIME_TYPES = Set.of(
            "video/mp4",
            "video/x-msvideo",     // AVI
            "video/webm",
            "video/quicktime",     // MOV
            "video/x-matroska",    // MKV
            "video/mpeg"
    );

    public static boolean createDirectoriesOneByOne(String path) {
        try{

            Path modifedPath=Paths.get(path);
            Files.createDirectories(modifedPath);
        }
        catch (Exception ex){
            log.info("Not able to create directory {}",path);
            return false;
        }

        return true;
    }
    public  boolean checkDiskSpaceAndFolder(String folderPathString, long fileSize)  {
        Path folderPath= Paths.get(folderPathString);
        java.io.File folder = folderPath.toFile();

        // Ensure folder exists
        if (!folder.exists()) {

            if(!createDirectoriesOneByOne(folderPathString)){
                return false;
            }

        }

        // Check available disk space
        java.io.File disk = new java.io.File(folderPath.toAbsolutePath().toString());
        long freeSpace = disk.getFreeSpace();

        if (freeSpace < Math.max(fileSize, MIN_REQUIRED_SPACE)) {
            log.error("Not enough disk space. Required: {} bytes, Available: {} bytes." , fileSize , freeSpace);
            return false;
        }

        return true;
    }
    public  boolean isValidVideo(MultipartFile file) {
        try {
            String mimeType = tika.detect(file.getInputStream());
            return ALLOWED_VIDEO_MIME_TYPES.contains(mimeType);
        } catch (IOException e) {
            return false;
        }
    }

    public  String detectMimeType(MultipartFile file) {
        try {
            return tika.detect(file.getInputStream());
        } catch (IOException e) {
            return MimeTypes.OCTET_STREAM;
        }
    }
    public void  fileValidation(MultipartFile file) throws Exception {
        // ✅ Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("audio/mpeg")) {
            throw new Exception("Only MP3 files are allowed");
        }
        if (!isValidVideo(file)) {
            throw new Exception("Only MP3 files are allowed");
        }
        // ✅ Validate file extension (optional but adds safety)
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".mp3")) {
            throw new Exception("Invalid file extension. Only .mp3 allowed.");
        }

        // ✅ Validate file size (in bytes: 10MB = 10 * 1024 * 1024)
        if (file.getSize() > 20 * 1024 * 1024) {
            throw new Exception("File size exceeds 10MB limit");
        }
    }
}
