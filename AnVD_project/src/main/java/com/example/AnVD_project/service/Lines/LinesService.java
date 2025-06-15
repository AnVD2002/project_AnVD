package com.example.AnVD_project.service.Lines;

import com.example.AnVD_project.dto.request.lines.LinesRequestDTO;

import java.util.List;

public interface LinesService {
    void saveLines(List<LinesRequestDTO> request);
}
