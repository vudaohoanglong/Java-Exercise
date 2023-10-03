package com.example.javaDemo.Controller;

import com.example.javaDemo.Service.FileService;
import com.example.javaDemo.Util.FileResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<FileResponse> updateFile(@RequestParam("file") MultipartFile file) {
        String uploadFileName = fileService.saveFile(file);
        String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(uploadFileName)
                .toUriString();
        return ResponseEntity.ok(new FileResponse(uploadFileName, fileDownloadURI, "File upload successfully"));
    }

    @GetMapping("/downloadFile/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename()+"\"")
                .body(resource);
    }
}
