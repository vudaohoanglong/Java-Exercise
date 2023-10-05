package com.example.javaDemo.Service;

import com.example.javaDemo.Config.FileUploadProperties;
import com.example.javaDemo.DTO.FileDTO;
import com.example.javaDemo.Entity._File;
import com.example.javaDemo.Entity._FileEs;
import com.example.javaDemo.Exception.FileNotFoundException;
import com.example.javaDemo.Exception.FileStorageException;
import com.example.javaDemo.Repository.FileEsRepo;
import com.example.javaDemo.Repository.FileRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FileService {

    private final Path dirLocation;
    private final FileRepo fileRepo;
    private final FileEsRepo fileEsRepo;
    @Autowired
    public FileService(FileUploadProperties fileUploadProperties, FileRepo fileRepo, FileEsRepo fileEsRepo) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
        this.fileRepo = fileRepo;
        this.fileEsRepo = fileEsRepo;
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
            Files.copy(multipartFile.getInputStream(),dfile, StandardCopyOption.REPLACE_EXISTING); // save file to local

            _File newFile = _File.builder()
                    .fileName(filename)
                    .build();

            _File existFile = fileRepo.findByFileName(filename); // check exist file name
            if (existFile == null) fileRepo.save(newFile); // add file name into mysql database

            existFile = fileRepo.findByFileName(filename);
            _FileEs newFileContent = _FileEs.builder()
                    .fileId(existFile.getFileId())
                    .fileContent(getContent(multipartFile))
                    .build();
            fileEsRepo.save(newFileContent); // add file content into elasticsearch database

            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not upload file");
        }
    }
/*
* Get content from multipartFile and convert to String
* Accept doc and docx file only.
*
* */
    private String getContent(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        // create temp file to transfer data
        File tmp = File.createTempFile("temp","");
        multipartFile.transferTo(tmp);
        // create stream from temporary file to string builder
        FileInputStream fis = new FileInputStream(tmp.getAbsolutePath());
        XWPFDocument xwpfDocument = new XWPFDocument(OPCPackage.open(fis));
        List<XWPFParagraph> paragraphList = xwpfDocument.getParagraphs();
        StringBuilder content = new StringBuilder();
        for (XWPFParagraph paragraph: paragraphList) {
            content.append(paragraph.getText()).append(" ");
        }
        fis.close();
        return content.toString();
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

    @Transactional
    public Object deleteAll() { // dev-only // do not use
        fileRepo.deleteAll();
        fileEsRepo.deleteAll();
        return true;
    }

    public Object searchFiles(String text) { // searching files have text in content
        List<_FileEs> resultFileEs = fileEsRepo.searchFiles(text); // return all FileEs entity
        Iterable<Integer> resultFileEsId = resultFileEs.stream().map(e->e.getFileId()).toList(); // get Id from resultFileEs
        List<_File> resultFile = fileRepo.findAllById(resultFileEsId); // get File enty with Ids got above
        List<FileDTO> result = new ArrayList<>();
        for (int i = 0; i < resultFile.size(); ++i) {
            result.add(new FileDTO(resultFile.get(i).getFileName(),resultFileEs.get(i).getFileContent())); // create list of FileDTO
        }
        return result;
    }
}
