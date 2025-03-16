package com.example.AnVD_project.DTO;

import lombok.*;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class PageableResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
}
