package com.example.AnVD_project.DTO.Request.Product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrudProductRequestDTO {

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
