package com.example.javaDemo.Repository;

import com.example.javaDemo.DTO.FileDTO;
import com.example.javaDemo.Entity._FileEs;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileEsRepo extends ElasticsearchRepository<_FileEs, Integer> {
    @Query("{\"bool\": {\"must\": [{\"match\": {\"file_content\": \"?0\"}}]}}") // search matching with text in file_content field
    List<_FileEs> searchFiles(String text);
}
