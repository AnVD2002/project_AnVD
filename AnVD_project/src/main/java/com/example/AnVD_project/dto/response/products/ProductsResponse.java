package com.example.AnVD_project.dto.response.products;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {
    private String cdLine;
    private String nmLine;
    private List<CategoryGroupDTO> groupByCategory;
}
