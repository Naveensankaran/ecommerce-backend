package com.naveen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveen.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{
	 int countByProductId(Long id);
	 
	 List<ProductReview> findAllByProductId(Long productId);
}
