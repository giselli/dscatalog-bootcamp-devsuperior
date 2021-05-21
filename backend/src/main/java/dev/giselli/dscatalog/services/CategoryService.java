package dev.giselli.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.giselli.dscatalog.entities.Category;
import dev.giselli.dscatalog.repositories.CategoryRepository;

@Service
/*
 * esta anotação indica que esta classe participará como um componente que
 * participará do sistema de injeção de depência automatizado do Spring, ou
 * seja, ele gerenciará as instâncias das dependências dos objetos tipo
 * CategoryService é um objeto da camada de serviço
 */
public class CategoryService {

	@Autowired
	// o Spring injetará uma dependência válida do CategoryRepository
	private CategoryRepository repository;

	public List<Category> findAll() {
		// deve acessar o repository e chamar as categorias no banco de dados
		return repository.findAll();
	}
}
