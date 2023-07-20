package duan.sportify.service;

import java.util.List;

import duan.sportify.dto.CategoryDTO;
import duan.sportify.dto.SportTypeDTO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Categories;
import duan.sportify.entities.Sporttype;



public interface SportTypeService {
	List<Sporttype> findAll();
	
	public List<Sporttype> getAll();
	
	public SportTypeDTO getOne(Integer id);

	public void delete(Integer id);

	SportTypeDTO createOrUpdate(SportTypeDTO dto);
	
	List<Sporttype> findSporttypeById(String id);
}
