package com.lifecourse.course_service.modules.filemanager.domain;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

@Slf4j
@NoArgsConstructor
public class File {

    private static final String UPLOAD_DIR = "/var/www/html/uploaded_songs/";


    public  String getUploadDir(String additionalDir){
        return  UPLOAD_DIR+additionalDir;
    }
    public  String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return ""; // No extension found
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }




    public  String sanitizeFileName(String fileName) {
        // Normalize Unicode characters (e.g., convert "é" to "e")
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Remove invalid characters (Windows, Linux, Mac restrictions)
        String sanitized = normalized.replaceAll("[\\\\/:*?\"<>|]", "_");

        // Remove leading/trailing spaces and multiple spaces
        sanitized = sanitized.trim().replaceAll("\\s+", "_");

        // Limit length (optional, e.g., 255 chars max for most file systems)
        int maxLength = 255;
        return sanitized.length() > maxLength ? sanitized.substring(0, maxLength) : sanitized;
    }

    /**
     * Returns the size of a file in bytes.
     *
     * @param filePath the absolute or relative path to the file
     * @return size in bytes
     * @throws IOException if the file does not exist or cannot be read
     */
    public static long getFileSizeInBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.size(path);
    }

    // Optional: method to return size in human-readable format
    private  String getReadableSize(String filePath) throws IOException {
        long size = getFileSizeInBytes(filePath);
        return formatSize(size);
    }

    private  String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String unit = "KMGTPE".charAt(exp - 1) + "B";
        return String.format("%.2f %s", bytes / Math.pow(1024, exp), unit);
    }

    public synchronized void mergeChunks(String fileName, int totalChunks,String requestId) throws IOException {
        Path finalFilePath = Paths.get(UPLOAD_DIR+ fileName);
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(finalFilePath))) {
            for (int i = 1; i <= totalChunks; i++) {
                Path chunkPath = Paths.get(getUploadDir(requestId) + fileName + "." + i);
                try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(chunkPath))) {
                    byte[] buffer = new byte[8192];  // 8KB buffer
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                Files.delete(chunkPath);  // Remove chunk after merging
            }
        }
    }


}
