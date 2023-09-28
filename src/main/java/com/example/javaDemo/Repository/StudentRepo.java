package com.example.javaDemo.Repository;

import com.example.javaDemo.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    @Query(value = "select a from Student s left join fetch StudentInfo a on s.studentId = a.student.studentId")
    List<Object> findAllStudent();

    @Query(value = "select a from Student s left join fetch StudentInfo a on s.studentId = a.student.studentId " +
            "where (?1 is null or s.studentName like %?1%)" +
            " and (?2 is null or s.studentId = ?2)")
    List<Object> findAllStudent(String studentName, Integer studentId);
}