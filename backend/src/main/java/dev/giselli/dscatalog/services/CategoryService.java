package dev.giselli.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.giselli.dscatalog.dto.CategoryDTO;
import dev.giselli.dscatalog.entities.Category;
import dev.giselli.dscatalog.repositories.CategoryRepository;
import dev.giselli.dscatalog.services.exceptions.DatabaseException;
import dev.giselli.dscatalog.services.exceptions.ResourceNotFoundException;

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
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		// deve acessar o repository e chamar as categorias no banco de dados
		// return repository.findAll();
		Page<Category> list = repository.findAll(pageRequest);

		return list.map(x -> new CategoryDTO(x));
		// collect transforma novamente o stream em lista

		/*
		 * List<CategoryDTO> listDto = new ArrayList<>(); for (Category cat : list) {
		 * listDto.add(new CategoryDTO(cat)); } return listDto; }
		 */

	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		// Com o o Optional o retorno da busca nunca será nulo
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		// objeto DTO deve ser convertido para um objeto Category (entidade)
		Category entity = new Category();
		entity.setName(dto.getName());
		// para salvar:
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	/*
	 * @Transactional public CategoryDTO udpate(Long id, CategoryDTO dto) {
	 * 
	 * try { Category entity = repository.getOne(id); // getOne só vai no banco de
	 * dados quando se mandar salvar entity.setName(dto.getName()); entity =
	 * repository.save(entity); return new CategoryDTO(entity); } catch
	 * (EntityNotFoundException e) { throw new
	 * ResourceNotFoundException("ID not found " + id); }
	 * 
	 * }
	 */
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); // getOne do not access database until you save the entity
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(String.format("ID not found [%d]", id));
		}
	}

	// não tem o Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found" + id);
		} catch (DataIntegrityViolationException e) {
			// tentar deletar algo que não pode - integridade referencial no banco
			throw new DatabaseException("Integrity violaton");
		}
	}
}
