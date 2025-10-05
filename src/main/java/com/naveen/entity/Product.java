package com.naveen.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
	public Product() {
		super();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Name field is required")
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "Price field is required")
	private Double price;
	
	@NotBlank(message = "Description field is required")
	private String description;
	
	private Double rating = 0.0;
	
	@NotBlank(message = "Seller field is required")
	private String seller;
	
	@NotNull(message = "Stock field is required")
	private Integer stock;
	
	private Integer numOfReview = 0;
	
	private String category ;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "product_id")
	private List<ProductImage> image = new ArrayList<>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProductReview> review = new ArrayList<>();
	
	public Product(Long id, String name, Double price,String category,
			String description, Double rating, String seller, Integer stock, List<String> image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.rating = rating;
		this.seller = seller;
		this.stock = stock;
		this.image = image.stream().map( url -> new ProductImage(url,this)).collect(Collectors.toList());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public List<ProductReview> getReview() {
		return review;
	}
	public void setReview(List<ProductReview> review) {
		this.review = review;
	}
	public Integer getNumOfReview() {
		return numOfReview;
	}
	public void setNumOfReview(Integer numOfReview) {
		this.numOfReview = numOfReview;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<ProductImage> getImage() {
		return image;
	}
	public void setImage(List<ProductImage> image) {
		this.image = image;
	}
	
}