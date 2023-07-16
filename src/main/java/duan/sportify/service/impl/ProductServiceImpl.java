package duan.sportify.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


import duan.sportify.dao.ProductDAO;
import duan.sportify.dto.ProductDTO;

import duan.sportify.entities.Products;

import duan.sportify.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDAO productDAO;
	@Autowired
	MessageSource messageSource;
	String format = "dd/MM/yyyy";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	
	@Override
	public List<Products> findAll() {
		// TODO Auto-generated method stub
		
		return productDAO.findAll();
	}

	@Override
	public ProductDTO createOrUpdate(ProductDTO dto) {
		// tạo 1 đối tượng mới
		Products entity = new Products();
		
		// Sao chép dữ liệu từ DTO sang Entity
		BeanUtils.copyProperties(dto, entity);
		entity.setDatecreate(LocalDate.parse(dto.getDatecreate(),formatter));
		// Thực hiện lưu dữ liệu vào database
		Products storedProductDetails = productDAO.save(entity);
		// Trả về giá trị đã lưu trữ
		ProductDTO returnValue = new ProductDTO();
		BeanUtils.copyProperties(storedProductDetails, returnValue);
		returnValue.setDatecreate(storedProductDetails.getDatecreate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		return returnValue;
	}

	@Override
	public void delete(Integer id) {
		// gọi hàm xóa jpa
		productDAO.deleteById(id);
	}

	@Override
	public ProductDTO getProductById(Integer id) {
		// Tìm Holiday theo id
			Optional<Products> productOptional = productDAO.findById(id);
			// Nếu có Holiday thì thực hiện trả dữ liệu
			if (productOptional.isPresent()) {
				// Khai báo đối tượng entity để lưu giá trị tìm thấy
				Products entity = productOptional.get();
				// Sao chép dữ liệu từ Entity sang DTO
				ProductDTO dto = new ProductDTO();
				// Định dạng các trường dữ liệu ngày theo format "dd/MM/yyyy"
					
				BeanUtils.copyProperties(entity, dto);
				dto.setDatecreate(entity.getDatecreate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				return dto;
			} else {
				return null;
			}	
	}

	@Override
	public Products findByProductId(Integer id) {
		return productDAO.findByProductid(id);
	}
	
}
