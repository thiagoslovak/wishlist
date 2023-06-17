package com.app.wishlist.controller;

import com.app.wishlist.dto.ProductDto;
import com.app.wishlist.model.Product;
import com.app.wishlist.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private final ProductDto.RepresentationBuilder builder = new ProductDto.RepresentationBuilder();

    @GetMapping
    public List<ProductDto> findingAll() {
        List<Product> products = productService.findingAllProduct();

        return products.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findingById(@PathVariable final String id) {
        final Product product = productService.findingProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> creating(@RequestBody @Valid final ProductDto dto) {
        final Product product = productService.creatingProduct(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(product));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updating(@RequestBody @Valid final ProductDto dto) {
        final Product product = productService.updatingProduct(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(product));
    }

    @DeleteMapping("/{id}")
    public void deleting(@PathVariable final String id) {
        productService.deletingProduct(id);
    }
}