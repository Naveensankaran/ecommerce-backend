package com.naveen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String publicId;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	private String url; 
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	public ProductImage(String url, Product product) {
		super();
		this.url = "/uploads"+url;
		this.publicId = url;
		this.product = product;
	}
	public ProductImage() {
		super();
	}
	public ProductImage(Long id, String publicId, String url) {
		super();
		this.id = id;
		this.publicId = publicId;
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPublicId() {
		return publicId;
	}
	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
