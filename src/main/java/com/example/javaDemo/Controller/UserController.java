package com.example.javaDemo.Controller;

import com.example.javaDemo.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final StudentService studentService;
    @GetMapping("/search")
    ResponseEntity<Object> search(@RequestParam Map<String,Object> query) {
        return ResponseEntity.ok(studentService.searchStudent(query));
    }
}
