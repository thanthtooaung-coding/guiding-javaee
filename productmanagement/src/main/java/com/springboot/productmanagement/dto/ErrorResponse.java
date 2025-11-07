package com.springboot.productmanagement.dto;

public record ErrorResponse(
        int code,
        String message
) {}
