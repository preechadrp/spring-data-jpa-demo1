package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;

@RestController
public class ProductController {

	private final com.example.service.ProductService productService;

	public ProductController(com.example.service.ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public java.util.List<Product> getAllProduct() {
		return this.productService.getAllProducts();
	}
}
