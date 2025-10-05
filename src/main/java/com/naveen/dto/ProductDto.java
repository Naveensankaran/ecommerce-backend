package com.naveen.dto;
import java.util.List;
public class ProductDto {	
		private Long id;
		private String name;
		private Double price;
		private String description;
		private Double rating = 0.0;
		private String seller;		
		private Integer stock;		
		private Integer numOfReview = 0;		
		private String category ;
		private List<ProductImageDto> image ;
		private List<ProductReviewDto> review ;
		
		public ProductDto() {
			super();
			
		}
		
		public ProductDto(Long id, String name, Double price,String category, String description, Double rating, String seller, Integer stock) {
			super();
			this.id = id;
			this.name = name;
			this.price = price;
			this.category = category;
			this.description = description;
			this.rating = rating;
			this.seller = seller;
			this.stock = stock;
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
		public List<ProductReviewDto> getReview() {
			return review;
		}
		public void setReview(List<ProductReviewDto> review) {
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

		public List<ProductImageDto> getImage() {
			return image;
		}

		public void setImage(List<ProductImageDto> imageDto) {
			this.image = imageDto;
		}

		
		
	}


