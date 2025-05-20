package com.example.user_service.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FilterProductRequest {
    private Integer id;
    private String name;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
