package com.example.javaDemo.DTO;

import com.example.javaDemo.Entity.Student;
import com.example.javaDemo.Entity.StudentInfo;

public class StudentDTOConverter {
    public static void toStudentDTO(StudentDTO studentDTO, Student student, StudentInfo studentInfo) {
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setStudentCode(studentDTO.getStudentCode());
        studentDTO.setStudentName(studentDTO.getStudentName());
        studentDTO.setStudentInfoId(studentInfo.getInfoId());
        studentDTO.setAverageScore(studentInfo.getAverageScore());
        studentDTO.setAddress(studentInfo.getAddress());
        studentDTO.setDateOfBirth(studentInfo.getDateOfBirth());
    }
    public static void fromDTO(StudentDTO studentDTO, Student student, StudentInfo studentInfo) {
        student.setStudentCode(studentDTO.getStudentCode());
        student.setStudentName(studentDTO.getStudentName());
        student.setStudentId(studentDTO.getStudentId());

        studentInfo.setInfoId(studentDTO.getStudentInfoId());
        studentInfo.setAddress(studentDTO.getAddress());
        studentInfo.setAverageScore(studentDTO.getAverageScore());
        studentInfo.setDateOfBirth(studentDTO.getDateOfBirth());
    }
}
