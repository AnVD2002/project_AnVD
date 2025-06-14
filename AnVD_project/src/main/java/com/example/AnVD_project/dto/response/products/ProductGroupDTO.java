package com.example.AnVD_project.dto.response.products;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductGroupDTO {
    private String cdProduct;
    private Long productId;
    private String description;
    private String nmProduct;
    private Double costPrice;
    private Double sellingPrice;
    private String image;
}
