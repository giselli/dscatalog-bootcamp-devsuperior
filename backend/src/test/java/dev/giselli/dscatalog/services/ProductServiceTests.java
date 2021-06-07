package dev.giselli.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.giselli.dscatalog.entities.Product;
import dev.giselli.dscatalog.repositories.ProductRepository;
import dev.giselli.dscatalog.services.exceptions.DatabaseException;
import dev.giselli.dscatalog.services.exceptions.ResourceNotFoundException;
import dev.giselli.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		// o ID 4 não poderia ser apagado porque outro entidade depende deste
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		//quando chamar o findAll, passando qualquer valor
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		//simulando o comportamento do save do repository
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		//quando o findById encontra um id existente

		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		//quando o findById encontra um id inexistente
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		// não faça nada quando o método for chamado para um ID existente

		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		// quando chama o deleteById com um id que não existe

		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		//quando tenta deletar um objeto associado com outro - integridade referencial
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExisting() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExisting() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		// verifica se alguma chamada foi feita
		// times indica quantas vezes será chamado o deleteById no repository
	}
	
}
