package com.example.AnVD_project.service.Products;

import com.example.AnVD_project.dto.request.product.ProductRequestDTO;
import com.example.AnVD_project.dto.response.products.ProductsResponse;

import java.util.List;

public interface ProductService {
    void saveProducts(List<ProductRequestDTO> request);

    List<ProductsResponse> getDataProducts(String cdLine , String cdCategory, String cdGroup);
}
