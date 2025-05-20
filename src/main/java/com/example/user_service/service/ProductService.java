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
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Mono<Product> addProduct(ProductRequest request) {
        var product = new Product()
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setPrice(request.getPrice())
                .setCreatedAt(new Date());
        return productRepository.save(product);
    }

    public Mono<Void> deleteProduct(Integer id) {
        return productRepository.deleteById(id);
    }


    public Mono<Product> updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        return productRepository.findById(id.longValue()).flatMap(p->{
                    if (!StringUtils.isBlank(updateProductRequest.getName())) {
                        p.setName(updateProductRequest.getName());
                    }
                    if (!StringUtils.isBlank(updateProductRequest.getDescription())) {
                        p.setDescription(updateProductRequest.getDescription());
                    }
                    if (updateProductRequest.getPrice() != null) {
                        p.setPrice(updateProductRequest.getPrice());
                    }
                    return productRepository.save(p);

                })
                .switchIfEmpty(Mono.error( new DataNotFoundException("Data Product tidak ditemukan")));


    }
}
