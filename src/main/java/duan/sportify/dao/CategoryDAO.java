package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Categories;

public interface CategoryDAO extends JpaRepository<Categories, Integer>{

}
