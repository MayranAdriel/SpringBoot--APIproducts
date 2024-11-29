package com.mayran.apiProducts.controllers;

import com.mayran.apiProducts.dtos.ProductRecordDto;
import com.mayran.apiProducts.models.ProductModel;
import com.mayran.apiProducts.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(("/products"))
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productModelList = productRepository.findAll();

        if(!productModelList.isEmpty()){
            for(ProductModel product : productModelList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getById(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        optionalProductModel.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("allProducts"));

        return ResponseEntity.status(HttpStatus.FOUND).body(optionalProductModel.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        ProductModel productModel = optionalProductModel.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(name = "id") UUID id) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        productRepository.delete(optionalProductModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted " + optionalProductModel.get().getName() + " Successfully");
    }

    private ResponseEntity<Object> productNotFoundMessage() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
    }
}
