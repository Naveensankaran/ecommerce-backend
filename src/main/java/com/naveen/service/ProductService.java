package com.naveen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.naveen.dto.ProductDto;
import com.naveen.dto.ProductImageDto;
import com.naveen.dto.ProductReviewDto;
import com.naveen.entity.Product;
import com.naveen.entity.ProductImage;
import com.naveen.entity.ProductReview;
import com.naveen.repository.ProductRepository;
import com.naveen.repository.ProductReviewRepository;
import com.naveen.spec.ProductSpecification;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductReviewRepository reviewRepo;

    // ------------------- Get All Products with Pagination -------------------
    public Map<String, Object> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepo.findAll(pageable);

        List<ProductDto> dto = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("Products", dto);
        response.put("TotalProducts", products.getTotalElements());
        return response;
    }

    // ------------------- Convert Product Entity to DTO -------------------
    public ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setRating(product.getRating());
        dto.setSeller(product.getSeller());
        dto.setStock(product.getStock());
        dto.setNumOfReview(product.getNumOfReview());
        List<ProductReviewDto> reviewDto = (product.getReview() == null ? new ArrayList<>() : product.getReview().stream()
                .map(review -> {
                    ProductReviewDto r = new ProductReviewDto();
                    r.setId(review.getId());
                    r.setComment(review.getComment());
                    r.setRating(review.getRating());
                    return r;
                }).collect(Collectors.toList()));
        dto.setReview(reviewDto);

        // Handle null image list
        List<ProductImageDto> imageDto = (product.getImage() == null ? new ArrayList<>() : product.getImage().stream()
                .map(image -> {
                    ProductImageDto iDto = new ProductImageDto();
                    iDto.setUrl(image.getPublicId());
                    return iDto;
                }).collect(Collectors.toList()));
        dto.setImage(imageDto);
   
        return dto;
    }

    // ------------------- Get Product by ID -------------------
    public ProductDto getProductById(Long id) {
        Product product =  productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for this id: " + id));
        return convertToDto(product);
    }

    // ------------------- Search Products with Filters -------------------
    public List<Product> searchProducts(String category, Double min, Double max, String keyword, Double rating) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.priceBetween(min, max))
                .and(ProductSpecification.hasNameOrDescription(keyword))
                .and(ProductSpecification.ratingGreaterThen(rating));

        return productRepo.findAll(spec);
    }
    
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setRating(productDto.getRating());
        product.setSeller(productDto.getSeller());
        product.setStock(productDto.getStock());

        // Images (if present in request)
        if(productDto.getImage() != null && !productDto.getImage().isEmpty()) {
            productDto.getImage().forEach(imgDto -> {
                ProductImage image = new ProductImage();
                image.setUrl(imgDto.getUrl());
                image.setProduct(product);
                product.getImage().add(image);
            });
        }

        // Save entity
        Product saved = productRepo.save(product);
        return convertToDto(saved);
    }

 // ------------------- Update Products with ID -------------------
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setRating(productDto.getRating());
        product.setSeller(productDto.getSeller());
        product.setStock(productDto.getStock());

        // Update images
        if(productDto.getImage() != null) {
            product.getImage().clear(); // remove old images
            productDto.getImage().forEach(imgDto -> {
                ProductImage image = new ProductImage();
                image.setUrl(imgDto.getUrl());
                image.setProduct(product);
                product.getImage().add(image);
            });
        }

        Product saved = productRepo.save(product);
        return convertToDto(saved);
    }
    
    // ------------------- Delete Products with ID -------------------
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found for id: " + id));
        productRepo.delete(product);
    }


    // ------------------- Add Review -------------------
    public void addReview(ProductReviewDto reviewDto) {
        // Find product
        Product product = productRepo.findById(reviewDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Save review
        ProductReview review = new ProductReview();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setProduct(product);
        reviewRepo.save(review);

        // Recalculate product rating & numOfReview
        updateProductRatings(product);
    }

    // ------------------- Recalculate Rating & Review Count -------------------
    public void updateProductRatings(Product product) {
        List<ProductReview> reviews = reviewRepo.findAllByProductId(product.getId());
        int totalReviews = reviews.size();
        double averageRating = reviews.stream()
                .mapToDouble(ProductReview::getRating)
                .average()
                .orElse(0.0);

        // Round to 1 decimal
        averageRating = Math.round(averageRating * 10.0) / 10.0;

        product.setNumOfReview(totalReviews);
        product.setRating(averageRating);

        productRepo.save(product);
    }
}
