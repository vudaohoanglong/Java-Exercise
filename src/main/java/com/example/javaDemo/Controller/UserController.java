package com.example.javaDemo.Controller;

import com.example.javaDemo.DTO.StudentDTO;
import com.example.javaDemo.Entity.StudentInfo;
import com.example.javaDemo.Service.StudentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final StudentService studentService;
    @GetMapping("/search")
    ResponseEntity<Object> search(@RequestParam Map<String,Object> query) {
        Object response =  studentService.searchStudent(query);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/searchBeforeDate")
    ResponseEntity<Object> searchBeforeDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        System.out.println(date);
        return ResponseEntity.ok(studentService.searchStudentBeforeDate(date));
    }
    @PostMapping("/addStudent")
    ResponseEntity<Object> addStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.addStudent(studentDTO));
    }

}
