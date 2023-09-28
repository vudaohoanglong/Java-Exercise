package com.example.javaDemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Integer studentId;
    private String studentName;
    private String studentCode;
    private Integer studentInfoId;
    private Double avarageScore;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date dateOfBirth;
}
