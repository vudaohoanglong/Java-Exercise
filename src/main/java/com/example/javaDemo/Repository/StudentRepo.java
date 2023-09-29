package com.example.javaDemo.Repository;

import com.example.javaDemo.Entity.Student;
import com.example.javaDemo.Entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
     public static String getStudentDTO = "select new com.example.javaDemo.DTO.StudentDTO(s.studentId, s.studentName, s.studentCode, " +
             "si.infoId, si.address, si.averageScore, si.dateOfBirth) ";
    @Query(value = getStudentDTO +
            "from Student s left join fetch StudentInfo si on s.studentId = si.student.studentId")
    List<Object> findAllStudent();

    @Query(value = getStudentDTO +
            " from Student s left join fetch StudentInfo si on s.studentId = si.student.studentId " +
            "where (?1 is null or s.studentName like %?1%)" +
            " and (?2 is null or s.studentId = ?2)")
    List<Object> findAllStudent(String studentName, Integer studentId);
    @Query(value = getStudentDTO +
            "from Student s left join fetch StudentInfo si on s.studentId = si.student.studentId " +
            "where si.dateOfBirth <= ?1")
    List<Object> findByDateOfBirthBefore(Date date);

    Student findByStudentId(Integer studentId);
}