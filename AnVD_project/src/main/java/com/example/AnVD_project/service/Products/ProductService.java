package com.example.AnVD_project.service.Products;

import com.example.AnVD_project.DTO.Request.Product.ProductRequestDTO;
import com.example.AnVD_project.DTO.Response.Products.ProductsResponse;

import java.util.List;

public interface ProductService {
    void saveProducts(List<ProductRequestDTO> request);

    List<ProductsResponse> getDataProducts(String cdLine , String cdCategory, String cdGroup);
}
