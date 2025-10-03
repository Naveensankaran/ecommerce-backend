package com.naveen.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.dto.ProductDto;
import com.naveen.entity.Product;
import com.naveen.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@GetMapping
	public Map<String, Object> getAllProducts(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5")int size) 
	{
				
		return service.getAllProduct(page, size);
	}
	
	 @PostMapping
	    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
	        ProductDto savedProduct = service.createProduct(productDto);
	        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	    }
	 @PutMapping("/{id}")
	 public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
	     return service.updateProduct(id, productDto);
	 }
	 
	 @DeleteMapping("/{id}")
	 public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
	     service.deleteProduct(id);
	     return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
	 }


	@GetMapping("/{id}")
	public ProductDto getProductById(@PathVariable Long id) {
		return service.getProductById(id);	
	}
	
	@GetMapping("/search")
	public List<Product> searchProducts(@RequestParam(required = false) String category, @RequestParam(required = false) Double min,
			@RequestParam(required = false) Double max, @RequestParam(required = false) String keyword, @RequestParam(required = false) Double rating)
	{
		return service.searchProducts(category, min, max, keyword,rating);
	}

}