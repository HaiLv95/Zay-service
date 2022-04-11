package com.zayshop.service;

import com.zayshop.config.StorageConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService {

    //khai báo đường dẫn gốc
    private final Path rootLocation;

    //contructor set rootLocation = location config in StrorageConfig
    public FileSystemStorageService(StorageConfig storageConfig) {
        this.rootLocation = Paths.get(storageConfig.getLocation());
    }

    //thay đổi tên file
    public String getStoredFileName(MultipartFile file, String id) {
        //file.getOriginalFilename() => lấy tên file
        //FilenameUtils.getExtension(file.getOriginalFilename()) lấy duôi file
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "." + ext;
    }

    public void store(MultipartFile file, String storedFilename) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file");
            }
            //Path.resolve => lấy đường dẫn gốc
            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();

            //so sánh đường dẫn full của file với đường dẫn của rootLocation. nếu k trùng nhau trả về lỗi
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new Exception("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                //copy file vào thư mục rootLocation
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new Exception("Failed to store file", e);
        }
    }

    //lấy đường dẫn gốc của file
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    //lấy resource của file trong thư mục lưu file
    public Resource loadAsResource(String fileName) throws Exception {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Could not read file: " + fileName);
        }
    }
    //khởi tạo thư mục chứa file
    public void init() throws Exception {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception e) {
            throw new Exception("Could Not Initialize storage", e);
        }
    }

}
