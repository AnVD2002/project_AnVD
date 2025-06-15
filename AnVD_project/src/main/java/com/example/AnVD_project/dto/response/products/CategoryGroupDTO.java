package com.example.AnVD_project.dto.response.products;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryGroupDTO {
    String cdCategory;
    String nmCategory;
    List<GroupGroupDTO> groupGroupDTO;
}
