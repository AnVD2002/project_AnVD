package com.example.AnVD_project.service;

import com.example.AnVD_project.DTO.PageableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PaginationImpl implements PaginationService {
    public <T> ResponseEntity<PageableResponse<T>> paginate(List<T> dataList, int page, int size) {
        if (dataList == null || dataList.isEmpty()) {
            PageableResponse<T> response = new PageableResponse<T>(List.of(), page, 0,0,0);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, dataList.size());
        if (fromIndex >= dataList.size()) {
            PageableResponse<T> response = new PageableResponse<>(List.of(), page, 0,0,0);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<T> paginatedList = dataList.subList(fromIndex, toIndex);

        int totalPages = (int) Math.ceil((double) dataList.size() / size);

        PageableResponse<T> response = new PageableResponse<>(paginatedList, page, size, totalPages, dataList.size());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
