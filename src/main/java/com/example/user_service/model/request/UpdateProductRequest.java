package com.example.user_service.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UpdateProductRequest {

    private String name;

    private BigDecimal price;

    private String description;

}