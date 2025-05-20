package com.example.user_service.controller;


import com.example.user_service.model.entity.Product;
import com.example.user_service.model.request.FilterProductRequest;
import com.example.user_service.model.request.ProductRequest;
import com.example.user_service.model.request.UpdateProductRequest;
import com.example.user_service.service.GetProductService;
import com.example.user_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final GetProductService getProductService;

    @PostMapping("/add-product")
    public Mono<ResponseEntity<?>> add(@RequestBody ProductRequest request){
        return productService.addProduct(request)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }
    @DeleteMapping("/delete-product")
    public Mono<ResponseEntity<Object>> delete(@RequestParam Integer id){
       return productService.deleteProduct(id)
               .then(Mono.just(ResponseEntity.noContent().build()))
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/get-product")
    public ResponseEntity<?> getProduct(@RequestBody FilterProductRequest request){
        return getProductService.getProduct(request.getId(),request.getName(),request.getMinPrice(),request.getMaxPrice());
    }
    @PatchMapping("/update-product")
    public Mono<ResponseEntity<Product>> updateProduct(@RequestParam(value = "id") Integer id, @RequestBody @Valid UpdateProductRequest request){
        return productService.updateProduct(id,request)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
