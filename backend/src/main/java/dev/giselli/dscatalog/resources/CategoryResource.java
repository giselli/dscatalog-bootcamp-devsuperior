package dev.giselli.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.giselli.dscatalog.entities.Category;
import dev.giselli.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
//rota Rest do recurso -> normalmente se coloca no plural
public class CategoryResource {
//recurso da entidade Category

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
//1º endpoint (rota possível que responderá)
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

}
