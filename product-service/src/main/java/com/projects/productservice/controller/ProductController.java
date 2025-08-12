package com.projects.productservice.controller;

import com.projects.core.product.ProductCreatedReq;
import com.projects.core.product.ProductCreatedRes;
import com.projects.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Async Sending messages in Kafka
     */
    @RequestMapping("/create")
    public ResponseEntity<ProductCreatedRes> create(@RequestBody ProductCreatedReq productCreatedReq) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createAsync(productCreatedReq));
    }

    /**
     * Sync Sending messages in Kafka
     */
    @RequestMapping("/create/sync")
    public ResponseEntity<ProductCreatedRes> createAsync(@RequestBody ProductCreatedReq productCreatedReq) throws Exception {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createSync(productCreatedReq));
    }
}
