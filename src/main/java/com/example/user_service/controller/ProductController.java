package com.example.user_service.controller;


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

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final GetProductService getProductService;

    @PostMapping("/add-product")
    public ResponseEntity<?> add(@RequestBody ProductRequest request){
        productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Data Product Berhasil Ditambahkan");
    }
    @DeleteMapping("/delete-product")
    public ResponseEntity<?> delete(@RequestParam Integer id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data Berhasil di delete");
    }
    @GetMapping("/get-product")
    public ResponseEntity<?> getProduct(@RequestBody FilterProductRequest request){
        return getProductService.getProduct(request.getId(),request.getName(),request.getMinPrice(),request.getMaxPrice());
    }
    @PatchMapping("/update-product")
    public ResponseEntity<?> updateProduct(@RequestParam(value = "id") Integer id,@RequestBody @Valid UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }
}
