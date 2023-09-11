package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getListAllProduct(){
        return productRepository.findAll();
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public Optional<Product> findbyId(String id){
        return productRepository.findById(id);
    }

    public void deletProductbyId(String id){
        productRepository.deleteById(id);
    }

    public Product findById(String id) {
        return productRepository.findById(id).get();
    }
}
