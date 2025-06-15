package com.example.AnVD_project.service;

import com.example.AnVD_project.dto.PageableResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaginationService {
    public <T> ResponseEntity<PageableResponse<T>> paginate(List<T> dataList, int page, int size);
}
