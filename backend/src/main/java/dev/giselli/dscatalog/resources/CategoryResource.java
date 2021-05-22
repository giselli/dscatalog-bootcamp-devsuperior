package dev.giselli.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.giselli.dscatalog.dto.CategoryDTO;
import dev.giselli.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
//rota Rest do recurso -> normalmente se coloca no plural
public class CategoryResource {
//recurso da entidade Category

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
//1º endpoint (rota possível que responderá)
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	// acrescenta o id na rota básica
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		// @PathVariable faz um pré processamento na hora de compilar para configurar
		// o webservice para receber uma variável recebida com a variável do Java
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		// inserindo uma nova categoria
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
		// se der certo o created retorna o código 201
	}

	@PutMapping(value = "/{id}") // para atualizar
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		// inserindo uma nova categoria
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);

	}

}
