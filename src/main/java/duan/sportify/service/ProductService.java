package duan.sportify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import duan.sportify.dto.ProductDTO;
import duan.sportify.entities.Products;


@Service
public interface ProductService {
	// hàm Hiển thị danh sách 
	List<Products> findAll();
	// hàm tạo và update
	ProductDTO createOrUpdate(ProductDTO dto);
	// hàm xóa
	void delete(Integer id);
	// hàm tìm theo id
	public ProductDTO getProductById(Integer id);
	// hàm tìm theo id
	Products findByProductId(Integer id);
}
