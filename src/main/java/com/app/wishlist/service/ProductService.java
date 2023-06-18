package com.app.wishlist.service;

import com.app.wishlist.model.Product;
import com.app.wishlist.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findingAllProduct() {
        return productRepository.findAll();
    }

    public Product findingProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The product does not exist."));
    }

    public Product creatingProduct(Product product) {
        if(checkingEqualProduct(product)) {
            logger.info("The product is equals.");
            return productRepository.findByName(product.getName());
        }

        try {
            product = productRepository.save(product);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return product;
    }

    public Product updatingProduct(Product product) {
        verifyIfIdIsValid(product.getId());

        try {
            product = productRepository.save(product);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return product;
    }

    public void deletingProduct(String id) {
        verifyIfIdIsValid(id);
        productRepository.deleteById(id);
    }

    public boolean checkingEqualProduct(Product product) {
        final Product productForChecking = productRepository.findByName(product.getName());

        if(productForChecking == null) {
            return false;
        }

        return product.getName().equals(productForChecking.getName());
    }

    public void verifyIfIdIsValid(String id) {
        findingProductById(id);
    }
}