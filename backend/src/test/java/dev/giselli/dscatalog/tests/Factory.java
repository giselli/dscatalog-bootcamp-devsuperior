package dev.giselli.dscatalog.tests;

import java.time.Instant;

import dev.giselli.dscatalog.dto.ProductDTO;
import dev.giselli.dscatalog.entities.Category;
import dev.giselli.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone","GoodPhone",800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(2L, "Electronics"));
		
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
		
				
	}

}
