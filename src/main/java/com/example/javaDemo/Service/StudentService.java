package com.example.javaDemo.Service;

import com.example.javaDemo.Repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    public Object searchStudent(Map<String, Object> query) {
        if (query.isEmpty()) {
            return studentRepo.findAllStudent();
        }
        else {
            return studentRepo.findAllStudent((String) query.get("studentName"), (Integer) query.get("studentId"));
        }
    }
}
