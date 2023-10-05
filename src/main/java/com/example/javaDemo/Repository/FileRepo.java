package com.example.javaDemo.Repository;

import com.example.javaDemo.Entity._File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<_File, Integer> {
    _File findByFileName(String filename);
}
