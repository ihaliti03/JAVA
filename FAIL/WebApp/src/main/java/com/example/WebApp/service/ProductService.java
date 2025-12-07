package com.example.WebApp.service;

import com.example.WebApp.model.Product;
import com.example.WebApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

//    List<Product> products = new ArrayList<>(Arrays.asList( //gotta study this
//            new Product(101, "Iphone", 5000),
//            new Product(102, "Samsung", 3500),
//            new Product(103, "Huawei", 2000)
//        )
//    );//khaa cfar kllapash aa

    public List<Product> getProducts(){
        return repo.findAll();
    }

    // i dont fully understand this function
    public Product getProductById(int prodId) {
        return repo.findById(prodId).get();    }

    public void addProduct(Product product) {
        repo.save(product);
    }

    public void updateProduct(Product product) {
        repo.save(product);
    }

    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);
    }
}
