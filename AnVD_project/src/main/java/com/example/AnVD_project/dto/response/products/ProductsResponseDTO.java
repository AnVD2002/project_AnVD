package com.example.AnVD_project.DTO.Response.Products;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponseDTO {
    private String cdLine;
    private String nmLine;
    private String cdCategory;
    private String nmCategory;
    private String cdGroup;
    private String nmGroup;
    private String cdProduct;
    private Long productId;
    private String description;
    private String nmProduct;
    private Double costPrice;
    private Double sellingPrice;
    private String image;
}
