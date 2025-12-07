package com.example.restdemo.Controller;

import com.example.restdemo.POJO.Product;
import com.example.restdemo.Repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
public class ProductController {

//    private final List<Product> productList = new ArrayList<>(Arrays.asList(
//            new Product(1, "Laptop", 1200.50, 10),
//            new Product(2, "Smartphone", 800.99, 20),
//            new Product(3, "Headphones", 150.75, 30),
//            new Product(4, "Keyboard", 50.25, 15),
//            new Product(5, "Monitor", 300.00, 5)
//    ));


    @Autowired
    private IProductRepository productRepository;


    @GetMapping("/products")
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

//    @GetMapping("/products/{id}")
//    public Product getProduct(@PathVariable int id) {
//        return productList.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
//    }
//
//    @PostMapping("/products")
//    public Product addProduct(@RequestBody Product product) {
//        productList.add(product);
//        return product;
//    }
//
//    @PutMapping("/products/{id}")
//    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
//        product.setId(id);
//        productList.add(product);
//        return product;
//    }
//
//    @DeleteMapping("/products/{id}")
//    public String deleteProduct(@PathVariable int id) {
//        Iterator<Product> iterator = productList.iterator();
//        while (iterator.hasNext()) {
//            Product product = iterator.next();
//            if (product.getId() == id) {
//                iterator.remove();
//                return "Product with ID " + id + " deleted successfully.";
//            }
//        }
//        return "Product with ID " + id + " not found.";
//    }


}
