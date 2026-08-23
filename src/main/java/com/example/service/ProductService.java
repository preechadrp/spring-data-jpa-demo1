package com.example.service;

import org.springframework.stereotype.Service;

import com.example.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public java.util.List<com.example.model.Product> getAllProducts() {
		return productRepository.findAll();
	}

}
