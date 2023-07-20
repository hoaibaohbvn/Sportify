package duan.sportify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import duan.sportify.dto.CategoryDTO;
import duan.sportify.entities.Categories;


@Service
public interface CategoryService {
	public List<Categories> getAll();
	
	public CategoryDTO getOne(Integer id);

	public void delete(Integer id);

	CategoryDTO createOrUpdate(CategoryDTO dto);

	Categories findByCategoryid(Integer categoryid);
	
}
