package com.example.AnVD_project.enums;

import lombok.*;

@RequiredArgsConstructor

@AllArgsConstructor
@Getter
public enum ResponseEnum {

    INVALID_INPUT("400", "Invalid input data"),
    PRODUCT_NOT_FOUND("404", "Product not found"),
    PRODUCT_ALREADY_EXISTS("409", "Product already exists"),
    INTERNAL_SERVER_ERROR("500", "Internal server error"),

    SUCCESS("200", "Request processed successfully"),
    CREATED("201", "Resource created successfully");

    private final String code;
    private final String message;
}
