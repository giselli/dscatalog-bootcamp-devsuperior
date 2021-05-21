package dev.giselli.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.giselli.dscatalog.dto.CategoryDTO;
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

	@Transactional(readOnly = true) // readOnly não trava o banco de dados
	// importar do Spring
	// vários métodos relacionados a uma transação
	public List<CategoryDTO> findAll() {
		// deve acessar o repository e chamar as categorias no banco de dados
		// return repository.findAll();
		List<Category> list = repository.findAll();

		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		// collect transforma novamente o stream em lista

		/*
		 * List<CategoryDTO> listDto = new ArrayList<>(); for (Category cat : list) {
		 * listDto.add(new CategoryDTO(cat)); } return listDto; }
		 */

	}
}