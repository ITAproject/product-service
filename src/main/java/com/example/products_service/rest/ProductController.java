package com.example.products_service.rest;

import com.example.products_service.dao.ProductRepository;
import com.example.products_service.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping(value="/products")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.toString());

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        logger.info("Getting all products.");
        return productRepository.findAll();
    }

    @GetMapping(value = "/{productId}")
    public Product getMovieById(@PathVariable String productId) {
        logger.info("Getting product with ID: " + productId);
        return productRepository.findById(productId).orElse(null);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        logger.info("Saving product.");
        logger.info(product.toString());
        return productRepository.save(product);
    }

    @PutMapping(value="/{productId}")
    public Product updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) {
        logger.info("Updating product with ID: " + productId);
        Product productToUpdate = new Product(productId, updatedProduct.name(), updatedProduct.description(), updatedProduct.price());
        return productRepository.save(productToUpdate );
    }

    @DeleteMapping(value="/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        logger.info("Product deleted");
        productRepository.deleteById(productId);
    }
}
