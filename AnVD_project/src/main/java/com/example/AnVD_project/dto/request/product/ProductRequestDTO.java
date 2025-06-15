package com.example.AnVD_project.dto.request.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    Long productId;

    Long lineId;

    Long groupId;

    Long categoryId;

    String lineCd;

    String groupCd;

    String categoryCd;

    String productName;

    String productDescription;

    Double costPrice;

    Double sellingPrice;

    String imageUrl;

    Boolean isDeleted;

}
