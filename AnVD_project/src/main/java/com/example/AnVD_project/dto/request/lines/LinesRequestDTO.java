package com.example.AnVD_project.dto.request.lines;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LinesRequestDTO {
    private Long id;
    private String nmLine;
    private String cdLine;
    private String image;
    private String description;
    private boolean isDeleted;
}
