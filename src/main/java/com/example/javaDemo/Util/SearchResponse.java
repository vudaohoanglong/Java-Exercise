package com.example.javaDemo.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SearchResponse {
    public final ResponseStatus responseStatus;
    public final List<Object> searchedStudentList;
}
