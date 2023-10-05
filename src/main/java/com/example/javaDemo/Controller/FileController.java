package com.example.javaDemo.Controller;

import com.example.javaDemo.Service.FileService;
import com.example.javaDemo.Util.FileResponse;
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
                .path("file/downloadFile/")
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

    @GetMapping("/searchFiles")
    public ResponseEntity<Object> searchFile(@RequestParam(name = "text") String text) {
        return ResponseEntity.ok(fileService.searchFiles(text));
    }

    @DeleteMapping("/deleteAll") // dev-only
    public ResponseEntity<Object> deleteAll() {
        return ResponseEntity.ok(fileService.deleteAll());
    }
}
