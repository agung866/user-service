package com.example.user_service.repository;

import com.example.user_service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends R2dbcRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Mono<Void> deleteById(int id);

//    Mono<Product> save(Product product);
}
