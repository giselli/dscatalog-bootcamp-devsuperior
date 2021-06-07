package dev.giselli.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import dev.giselli.dscatalog.entities.Product;
import dev.giselli.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRespositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception{
		//inicializa os valores antes de rodar os testes
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
	//testa se o save funciona quando  para um novo objeto quando o id dele é nulo
	
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		
		
	}
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
			
	}
	
	@Test
	public void deleteShouldThrowEmptyResultAccessExceptionWhenIdDoesNotExist() {
		//testando a busca por um id inválido
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnNonEmptyOptionWhenIdExists() {
		
		repository.findById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
			
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionWhenIdDoesNotExists() {
		
		repository.findById(nonExistingId);
		
		Optional<Product> result = repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
			
	}


}
