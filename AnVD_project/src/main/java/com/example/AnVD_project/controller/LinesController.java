package com.example.AnVD_project.controller;

import com.example.AnVD_project.dto.request.lines.LinesRequestDTO;
import com.example.AnVD_project.enums.RoleEnum;
import com.example.AnVD_project.enums.Secured;
import com.example.AnVD_project.service.Lines.LinesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/line")
@AllArgsConstructor
public class LinesController {
    @Autowired
    private final LinesService linesService;

    @PostMapping
    @Secured(roles = RoleEnum.USER)
    public ResponseEntity<?> saveLine(@RequestBody List<LinesRequestDTO> requests, HttpServletRequest request) {
        linesService.saveLines(requests);
        return ResponseEntity.ok().build();
    }
}
