package com.example.javaDemo.Service;

import com.example.javaDemo.DTO.StudentDTO;
import com.example.javaDemo.DTO.StudentDTOConverter;
import com.example.javaDemo.Entity.Student;
import com.example.javaDemo.Entity.StudentInfo;
import com.example.javaDemo.Repository.StudentRepo;
import com.example.javaDemo.Util.ResponseStatus;
import com.example.javaDemo.Util.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    public Object searchStudent(Map<String, Object> query) {
        if (query.isEmpty()) {
            return new SearchResponse(ResponseStatus.SUCCESS, studentRepo.findAllStudent());
        }
        else {
            String studentName = (String) query.get("studentName");
            Integer studentId = (Integer) query.get("studentId");
            if (studentName != null && ! validate(studentName)) {
                return new SearchResponse(ResponseStatus.VALIDATION_ERROR, new ArrayList<>());
            }
            return new SearchResponse(ResponseStatus.SUCCESS, studentRepo.findAllStudent(studentName, studentId));
        }
    }

    public static boolean validate(String name) {
        return name.length() <= 20;
    }
    public static boolean validate(Date date) {
        return date.before(new Date());
    }
    public Object searchStudentBeforeDate(String dateString) {
        Date date;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
             date = dateFormatter.parse(dateString);
        } catch (Exception e) {
            return new SearchResponse(ResponseStatus.VALIDATION_ERROR, new ArrayList<>());
        }
        if (date != null && !validate(date)) {
            return new SearchResponse(ResponseStatus.VALIDATION_ERROR, new ArrayList<>());
        }
        else {
            return new SearchResponse(ResponseStatus.SUCCESS, studentRepo.findByDateOfBirthBefore(date));
        }
    }

    public Object addStudent(StudentDTO studentDTO) { // not complete
        Student student = new Student();
        StudentInfo studentInfo = new StudentInfo();
        StudentDTOConverter.fromDTO(studentDTO,student,studentInfo);
        Student existStudent = studentRepo.findByStudentId(student.getStudentId());
        return null;
    }
}
