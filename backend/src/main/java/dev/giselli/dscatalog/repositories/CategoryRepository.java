package dev.giselli.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.giselli.dscatalog.entities.Category;

@Repository
//é um componente injetável pelo mecanismo de dependências do Spring
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
