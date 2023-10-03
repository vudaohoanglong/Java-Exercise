package com.example.javaDemo.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class FileResponse {
    private String filename;
    private String fileURL;
    private String message;
}
