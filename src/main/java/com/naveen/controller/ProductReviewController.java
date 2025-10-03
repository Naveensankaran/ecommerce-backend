package com.naveen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.dto.ProductReviewDto;
import com.naveen.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products/review")
public class ProductReviewController {

	@Autowired
	private ProductService service;
	@PostMapping
	public ResponseEntity<?> addReview(@RequestBody @Valid ProductReviewDto reviewDto) {
		 service.addReview(reviewDto);
		 return ResponseEntity.status(HttpStatus.CREATED).body("review is added");
	}
	
}
