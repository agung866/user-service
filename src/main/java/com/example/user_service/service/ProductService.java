package com.example.user_service.service;

import com.example.user_service.errorhandling.BadRequestException;
import com.example.user_service.errorhandling.DataNotFoundException;
import com.example.user_service.model.entity.Product;
import com.example.user_service.model.request.FilterProductRequest;
import com.example.user_service.model.request.ProductRequest;
import com.example.user_service.model.request.UpdateProductRequest;
import com.example.user_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductRequest request) {
        var product = new Product()
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setPrice(request.getPrice())
                .setCreatedAt(new Date());
        productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }


    public ResponseEntity<?> updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        var getProduct = productRepository.findById(id.longValue())
                .orElseThrow(() -> new DataNotFoundException("Data Product tidak ditemukan"));

        if (!StringUtils.isBlank(updateProductRequest.getName())) {
            getProduct.setName(updateProductRequest.getName());
        }
        if (!StringUtils.isBlank(updateProductRequest.getDescription())) {
            getProduct.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getPrice() != null) {
            getProduct.setPrice(updateProductRequest.getPrice());
        }
        productRepository.save(getProduct);
        return ResponseEntity.ok(getProduct);
    }
}
