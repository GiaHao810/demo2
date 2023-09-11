package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageUtil {
    private final static String defaultFolderPath = "C:/Users/Hao/Desktop/demo2/src/main/resources/static";

    private final static Logger log = LoggerFactory.getLogger(ImageUtil.class);
    public static File getFolderUpload() {
        File folderUpload = new File(defaultFolderPath);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

    public static void deleteImage(String imageURL) {
        String imagePath = defaultFolderPath + imageURL;

        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
            log.info("Image deleted: " + imageURL);
        } else {
            log.warn("Image not found: " + imageURL);
        }
    }

    public static String saveImage(MultipartFile image){
        String fileName = "\\images\\" + image.getOriginalFilename();
        File fileUrl = getFolderUpload();

        try {
            File file = new File(fileUrl, fileName);
            image.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }
}
