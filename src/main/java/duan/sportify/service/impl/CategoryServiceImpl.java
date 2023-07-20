package duan.sportify.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import duan.sportify.dao.AuthorizedDAO;
import duan.sportify.dao.CategoryDAO;
import duan.sportify.dto.CategoryDTO;
import duan.sportify.entities.Authorized;
import duan.sportify.entities.Categories;
import duan.sportify.service.AuthorizedService;
import duan.sportify.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	MessageSource messagesource;
	@Override
	public List<Categories> getAll() {
		// TODO Auto-generated method stub
		return categoryDAO.findAll();
	}

	@Override
	public CategoryDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		Optional<Categories> optional = categoryDAO.findById(id);
		if(optional.isPresent()) {
			Categories categories = optional.get();
			CategoryDTO categoryDTO = new CategoryDTO();
			BeanUtils.copyProperties(categories, categoryDTO);
			return categoryDTO;
		}else {
			return null;
		}
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		categoryDAO.deleteById(id);
	}

	@Override
	public CategoryDTO createOrUpdate(CategoryDTO dto) {
		// Tạo 1 đối tượng categories
		Categories categories = new Categories();
		// sao chép dữ liệu
		BeanUtils.copyProperties(dto, categories);
		// thực hiện lưu vào DB
		Categories categoriesDetail = categoryDAO.save(categories);
		// Trả về giá trị lưu trữ
		CategoryDTO returnCategoryDTO = new CategoryDTO();
		// Sao chép đối tưởng trả về lưu trữ
		BeanUtils.copyProperties(categoriesDetail, returnCategoryDTO);
		return returnCategoryDTO;
	}

	@Override
	public Categories findByCategoryid(Integer categoryid) {
		// TODO Auto-generated method stub
		return categoryDAO.findByCategoryid(categoryid);
	}
	

}
