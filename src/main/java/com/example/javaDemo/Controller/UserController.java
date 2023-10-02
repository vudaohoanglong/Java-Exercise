package com.example.javaDemo.Controller;

import com.example.javaDemo.DTO.StudentDTO;
import com.example.javaDemo.Entity.StudentInfo;
import com.example.javaDemo.Service.StudentService;
import com.example.javaDemo.Util.ResponseStatus;
import com.example.javaDemo.Util.SearchResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
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
    ResponseEntity<Object> search(@RequestParam Map<String,Object> query) { // search up students based on query
        SearchResponse response =  (SearchResponse) studentService.searchStudent(query);
        if (response.getResponseStatus() == ResponseStatus.VALIDATION_ERROR) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/searchBeforeDate") // search up students who have date of birth before input date
    ResponseEntity<Object> searchBeforeDate(@RequestParam String date) {
        SearchResponse response =  (SearchResponse) studentService.searchStudentBeforeDate(date);
        if (response.getResponseStatus() == ResponseStatus.VALIDATION_ERROR) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/addStudent") // not completed
    ResponseEntity<Object> addStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.addStudent(studentDTO));
    }

    @PutMapping("/editStudent") // not completed
    ResponseEntity<Object> editStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.addStudent(studentDTO));
    }
}
