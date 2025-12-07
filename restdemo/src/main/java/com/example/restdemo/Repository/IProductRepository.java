package com.example.restdemo.Repository;

import com.example.restdemo.POJO.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllById(String category);
    Product findById(int id);
}
