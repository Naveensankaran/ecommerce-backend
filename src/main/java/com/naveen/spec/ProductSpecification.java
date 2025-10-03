package com.naveen.spec;

import org.springframework.data.jpa.domain.Specification;

import com.naveen.entity.Product;

public class ProductSpecification {

	public static Specification<Product> hasCategory(String category){
		return (root, query, cb) -> category == null ? null : cb.equal(root.get("category"),category);
	}
	
	public static Specification<Product> priceBetween(Double min, Double max){
		return (root, query, cb) ->{
		if(min == null && max == null) return null;
		if(min == null)  return cb.lessThanOrEqualTo(root.get("price"), max);
		if(max == null)  return cb.greaterThanOrEqualTo(root.get("price"), min);
		return cb.between(root.get("price"), min, max);
		};
	}
	
	public static Specification<Product> hasNameOrDescription(String keyword){
		return (root, query, cb) ->{
			if(keyword == null || keyword.isEmpty()) return null;
			return cb.or(
					cb.like(root.get("name"), "%"+keyword.toLowerCase()+"%"),
					cb.like(root.get("description"), "%"+keyword.toLowerCase()+"%")
					);			
		};
	}
	
	public static Specification<Product> ratingGreaterThen(Double rating){
		return (root, query, cb) ->  cb.greaterThanOrEqualTo(root.get("rating"), rating);
	}
}