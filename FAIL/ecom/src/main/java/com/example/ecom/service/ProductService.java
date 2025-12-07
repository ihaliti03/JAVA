package com.example.ecom.service;

import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // Standard CRUD operations
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    // Handles product creation with image upload
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        // Set image metadata and binary data before saving
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repository.save(product);
    }

    // Updates existing product including new image
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        // Update image data for existing product
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repository.save(product);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    // Custom search using repository method
    public List<Product> searchProducts(String keyword) {
        return repository.searchProducts(keyword);
    }
}