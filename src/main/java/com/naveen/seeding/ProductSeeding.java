package com.naveen.seeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.naveen.entity.Product;
import com.naveen.repository.ProductRepository;
@Component
public class ProductSeeding implements CommandLineRunner{

	@Autowired
	ProductRepository repo ;
	
	@Override
	  public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            List<Product> productList = List.of(
                // ---------- Smartphones ----------
                new Product(null, "iPhone 15", 120000.0, "Smartphone",
                        "Latest iPhone with A16 Bionic chip", 4.8,
                        "Apple Store", 50 ,List.of("/products/iPhone-15.jpg")),

                new Product(null, "Samsung Galaxy S23", 95000.0, "Smartphone",
                        "Flagship Samsung phone with Snapdragon 8 Gen 2", 4.5,
                        "Samsung Store", 80 ,List.of("/products/Samsung_Galaxy_S23.jpg")),

                new Product(null, "OnePlus 11", 65000.0, "Smartphone",
                        "OnePlus flagship smartphone with Snapdragon 8 Gen 2", 4.4,
                        "OnePlus Store", 70 , List.of("/products/OnePlus_11.jpg")),

                new Product(null, "Google Pixel 8", 78000.0, "Smartphone",
                        "Google flagship phone with Tensor chip", 4.5,
                        "Google Store", 60 , List.of("/products/Google_Pixel_8.jpg")),

                new Product(null, "Realme GT Neo 5", 42000.0, "Smartphone",
                        "Fast-charging smartphone with Snapdragon 8+ Gen 1", 4.3,
                        "Realme Store", 90 , List.of("/products/Realme_GT_Neo_5.jpg")),

                // ---------- Laptops ----------
                new Product(null, "MacBook Pro M2", 180000.0, "Laptop",
                        "Apple MacBook Pro with M2 chip", 4.9,
                        "Apple Store", 25 , List.of("/products/MacBook_Pro_M2.jpg")),

                new Product(null, "Dell XPS 15", 160000.0, "Laptop",
                        "Dell flagship ultrabook", 4.6,
                        "Dell Store", 40 , List.of("/products/Dell_XPS_15.jpg")),

                new Product(null, "HP Spectre x360", 140000.0, "Laptop",
                        "Premium convertible laptop with Intel i7", 4.5,
                        "HP Store", 30 , List.of("/products/HP_Spectre_x360.jpg")),

                new Product(null, "Lenovo ThinkPad X1 Carbon", 155000.0, "Laptop",
                        "Business ultrabook with durability and power", 4.7,
                        "Lenovo Store", 35 , List.of("/products/Lenovo_ThinkPad_X1_Carbon.jpg")),

                new Product(null, "Asus ROG Zephyrus G14", 135000.0, "Laptop",
                        "Gaming laptop with Ryzen 9 and RTX GPU", 4.6,
                        "Asus Store", 45 , List.of("/products/Asus_ROG_Zephyrus_G14.jpg")),

                // ---------- Accessories ----------
                new Product(null, "Sony WH-1000XM5", 30000.0, "Headphones",
                        "Noise-cancelling wireless headphones", 4.7,
                        "Sony Official", 100 , List.of("/products/Sony_WH-1000XM5.jpg")),

                new Product(null, "Apple AirPods Pro 2", 25000.0, "Earbuds",
                        "Wireless earbuds with ANC and spatial audio", 4.5,
                        "Apple Store", 150 , List.of("/products/Apple_AirPods_Pro_2.jpg")),

                new Product(null, "Samsung Galaxy Watch 6", 32000.0, "Smartwatch",
                        "Fitness tracking smartwatch with AMOLED display", 4.4,
                        "Samsung Store", 85 , List.of("/products/Samsung_Galaxy_Watch_6.jpg")),

                new Product(null, "Fitbit Charge 6", 18000.0, "Smartwatch",
                        "Health-focused fitness tracker", 4.2,
                        "Fitbit Official", 120 , List.of("/products/Fitbit_Charge_6.jpg")),

                new Product(null, "Logitech MX Master 3S", 11000.0, "Accessories",
                        "Premium productivity mouse with ergonomic design", 4.8,
                        "Logitech Store", 200 , List.of("/products/Logitech_MX_Master_3S.jpg")),

                // ---------- Electronics ----------
                new Product(null, "Canon EOS R5", 250000.0, "Camera",
                        "Professional mirrorless camera", 4.8,
                        "Canon Store", 15 , List.of("/products/Canon_EOS_R5.jpg")),

                new Product(null, "LG OLED C3 TV", 220000.0, "Television",
                        "Premium 65-inch OLED Smart TV", 4.9,
                        "LG Electronics", 20 , List.of("/products/LG_OLED_C3_TV.jpg")),

                new Product(null, "Sony PlayStation 5", 60000.0, "Gaming Console",
                        "Next-gen gaming console from Sony", 4.9,
                        "Sony Store", 95 , List.of("/products/Sony_PlayStation_5.jpg")),

                new Product(null, "Xbox Series X", 55000.0, "Gaming Console",
                        "Next-gen gaming console from Microsoft", 4.7,
                        "Microsoft Store", 100 , List.of("/products/Xbox_Series_X.jpg")),

                new Product(null, "Nintendo Switch OLED", 40000.0, "Gaming Console",
                        "Hybrid handheld gaming console", 4.6,
                        "Nintendo Store", 80 , List.of("/products/Nintendo_Switch _OLED.jpg"))
            );

            repo.saveAll(productList);
            System.out.println("✅ 20 demo products seeded with categories");
        } else {
            System.out.println("⚠️ Products already exist");
        }
    }
}