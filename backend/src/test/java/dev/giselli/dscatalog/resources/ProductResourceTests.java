package dev.giselli.dscatalog.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import dev.giselli.dscatalog.dto.ProductDTO;
import dev.giselli.dscatalog.services.ProductService;
import dev.giselli.dscatalog.tests.Factory;

@WebMvcTest(ProductResource.class)
//para testes na camada web
public class ProductResourceTests {

	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private ProductService service;
	
	private ProductDTO productDTO;
	
	private PageImpl<ProductDTO> page;
	//objeto concreto
	
	@BeforeEach
	void setUp() throws Exception{
	//simulando o comportamento do findAllPaged do Service	
		
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception{
		mockmvc.perform(get("/products")).andExpect(status().isOk());	
		//perforn faz uma requisição http com o caminho products
	}
}
