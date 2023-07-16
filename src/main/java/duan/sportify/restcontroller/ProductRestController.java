package duan.sportify.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import duan.sportify.GlobalExceptionHandler;
import duan.sportify.dto.ProductDTO;
import duan.sportify.entities.Products;
import duan.sportify.service.ProductService;
import duan.sportify.utils.ErrorResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/products/")
public class ProductRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	ProductService productService;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public List<Products> getAllProduct(){
		return productService.findAll();
		
	}
	@PostMapping("createProduct")
	public ResponseEntity<HttpStatus> createHoliday(@Valid @RequestBody ProductDTO dto) {
		if (dto != null) {
			productService.createOrUpdate(dto);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
	@PutMapping("updateProduct")
	public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody ProductDTO dto) {
		if (dto != null) {
			productService.createOrUpdate(dto);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
	@DeleteMapping("deleteProduct/{id}")
	public ResponseEntity<HttpStatus> deleteHoliday(@PathVariable("id") Integer id) {
	    // Gọi service để xóa dữ liệu từ ID
		
		productService.delete(id);
	    
	    return ResponseEntity.ok().build();
	  }
	@GetMapping("newHoliday")
	public ResponseEntity<ProductDTO> newProduct() {

		ProductDTO newProductDTO = new ProductDTO();

		return ResponseEntity.ok(newProductDTO);
	}
	@GetMapping("edit/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
		ProductDTO productDTO = productService.getProductById(id);
		if (productDTO != null) {
			return ResponseEntity.ok(productDTO);
		} else {
//	    	Locale currentLocale = LocaleContextHolder.getLocale();
//	    	System.out.println("Ngôn ngữ hiện tại: " + currentLocale);

			// Lấy tin nhắn từ message.properties dựa trên ngôn ngữ hiện tại
			String errorMessage = messagesource.getMessage("NotFound.product.product", null,
					LocaleContextHolder.getLocale());
			// Ném ResponseStatusException với mã trạng thái 404 và thông báo lỗi
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
		}
	}
}
