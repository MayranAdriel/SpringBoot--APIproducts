package com.mayran.apiProducts.controllers;

import com.mayran.apiProducts.dtos.ProductRecordDto;
import com.mayran.apiProducts.models.ProductModel;
import com.mayran.apiProducts.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(("/products"))
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        return ResponseEntity.status(HttpStatus.FOUND).body(optionalProductModel.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if(optionalProductModel.isEmpty()) return productNotFoundMessage();

        ProductModel productModel = optionalProductModel.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));

    }

    private ResponseEntity<Object> productNotFoundMessage(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
    }
}
