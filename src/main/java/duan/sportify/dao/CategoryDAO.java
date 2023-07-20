package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Categories;

public interface CategoryDAO extends JpaRepository<Categories, Integer>{
	// Viết theo kiểu tối giản để gọi truy vấn tìm theo id
	Categories findByCategoryid(Integer categoryid);
}
