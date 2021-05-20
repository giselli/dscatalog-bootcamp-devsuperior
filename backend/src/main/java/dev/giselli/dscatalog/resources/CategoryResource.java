package dev.giselli.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.giselli.dscatalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
//rota Rest do recurso -> normalmente se coloca no plural
public class CategoryResource {
//recurso da entidade Catgory

	@GetMapping

	public ResponseEntity<List<Category>> findAll() {
//1º endpoint (rota possível que responderá)
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		// 1L - long
		list.add(new Category(2L, "Eletronics"));
		return ResponseEntity.ok().body(list);
	}

}
