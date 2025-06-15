package com.example.AnVD_project.controller;

import com.example.AnVD_project.dto.request.product.ProductRequestDTO;
import com.example.AnVD_project.dto.response.products.ProductsResponse;
import com.example.AnVD_project.service.Products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> crudProducts(@RequestBody List<ProductRequestDTO> request){
        productService.saveProducts(request);
        return new ResponseEntity<>("completed",HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductsResponse>> getStructuredProducts(
            @RequestParam(required = false) String cdLine,
            @RequestParam(required = false) String cdCategory,
            @RequestParam(required = false) String cdGroup
    ) {
        List<ProductsResponse> data = productService.getDataProducts(cdLine, cdCategory, cdGroup);
        return ResponseEntity.ok(data);
    }
}
