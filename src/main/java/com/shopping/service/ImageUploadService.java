package com.shopping.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Log4j2
public class ImageUploadService {
    private static final String UPLOAD_DIR = "C:/uploads/";

    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Image file is empty");
        }

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new IOException("Invalid file name");
            }

            String extension = getFileExtension(originalFileName);
            String fileName = System.currentTimeMillis() + "." + extension;
            String filePath = UPLOAD_DIR + fileName;

            // 원본 이미지를 BufferedImage로 읽기
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                throw new IOException("Failed to read image content");
            }

            // 리사이징 처리 너비 800px
            Thumbnails.of(originalImage)
                      .size(800, Integer.MAX_VALUE)
                      .outputFormat(extension) 
                      .toFile(new File(filePath));

            return "/images/" + fileName;
        } catch (IOException e) {
            log.error("File upload failed", e);
            throw new IOException("Image Upload Fail", e);
        }
    }

    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1).toLowerCase(); // 확장자를 소문자로 통일
        }
        return "jpg"; // 기본 확장자
    }
}
