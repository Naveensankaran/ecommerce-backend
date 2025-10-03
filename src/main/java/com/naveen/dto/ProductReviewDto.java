package com.naveen.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductReviewDto {
	 @NotNull(message = "Id cannot be blank")
	private Long id;
	 
    @Min(1) @Max(5)
    @NotNull(message = "Rating cannot be blank")
	private Double rating;
	@NotBlank(message = "comment cannot be blank")
	private String comment;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
