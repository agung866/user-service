package com.example.user_service.service;

import com.example.user_service.errorhandling.BadRequestException;
import com.example.user_service.model.entity.Product;
import com.example.user_service.model.request.FilterProductRequest;
import com.example.user_service.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class GetProductService {
    private final ProductRepository productRepository;

    @Cacheable(value = "product-search",key = "{#id, #name, #minPrice, #maxPrice}")
    public ResponseEntity<?> getProduct( Integer id,String name, BigDecimal minPrice,BigDecimal maxPrice) {

        Specification<Product> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), id)));
        }

        if (StringUtils.isNotBlank(name)) {
            spec = spec.and(((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%")));
        }

        validationPrice(minPrice, maxPrice);

        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        var getAllProduct = productRepository.findAll(spec);

        return ResponseEntity.ok(getAllProduct);

    }

    private void validationPrice(BigDecimal min, BigDecimal max) {
        if (min != null && max != null && min.compareTo(max) > 0) {
            throw new BadRequestException("masukan harga maximum lebih tinggi dari pada harga minimum");
        }
    }
}
