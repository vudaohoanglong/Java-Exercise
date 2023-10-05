package com.example.javaDemo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "file")
@AllArgsConstructor
@NoArgsConstructor
public class FileEs {
    @Id
    @Field(name = "file_id", type = FieldType.Integer)
    private Integer fileId;

    @Field(name = "file_content", type = FieldType.Text)
    private String fileContent;

}
