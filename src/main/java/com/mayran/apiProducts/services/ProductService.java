package com.mayran.apiProducts.services;

import com.mayran.apiProducts.controllers.ProductController;
import com.mayran.apiProducts.dtos.ProductRecordDto;
import com.mayran.apiProducts.models.ProductModel;
import com.mayran.apiProducts.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<ProductModel> saveProduct(ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productModelList = productRepository.findAll();

        if (!productModelList.isEmpty()) {
            for (ProductModel productModel : productModelList) {
                UUID id = productModel.getIdProduct();
                productModel.add(linkTo(methodOn(ProductController.class).getById(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelList);
    }

    public ResponseEntity<Object> getById(UUID id) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        optionalProductModel.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("allProducts"));

        return ResponseEntity.status(HttpStatus.OK).body(optionalProductModel.get());
    }

    public ResponseEntity<Object> updateProduct(UUID id, ProductRecordDto productRecordDto) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        ProductModel productModel = optionalProductModel.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productRepository.save(productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productModel);
    }

    public ResponseEntity<Object> deleteProduct(UUID id) {
        Optional<ProductModel> optionalProductModel = productRepository.findById(id);

        if (optionalProductModel.isEmpty()) return productNotFoundMessage();

        productRepository.delete(optionalProductModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Delete Successfully");
    }

    private ResponseEntity<Object> productNotFoundMessage() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
    }
}
