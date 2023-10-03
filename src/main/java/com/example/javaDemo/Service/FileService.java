package com.example.javaDemo.Service;

import com.example.javaDemo.Config.FileUploadProperties;
import com.example.javaDemo.Exception.FileNotFoundException;
import com.example.javaDemo.Exception.FileStorageException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final Path dirLocation;
    @Autowired
    public FileService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(dirLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create upload dir");
        }
    }

    public String saveFile(MultipartFile multipartFile) {
        try {
            String filename = multipartFile.getOriginalFilename();
            Path dfile = this.dirLocation.resolve(filename);
            Files.copy(multipartFile.getInputStream(),dfile, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not upload file");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = this.dirLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else throw new FileNotFoundException("Could not find file");
        } catch (Exception e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
