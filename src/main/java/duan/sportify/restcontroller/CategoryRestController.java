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
import duan.sportify.dao.CategoryDAO;
import duan.sportify.dto.CategoryDTO;
import duan.sportify.entities.Categories;
import duan.sportify.service.CategoryService;
import duan.sportify.utils.ErrorResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/category-product")
public class CategoryRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	CategoryService categoryService;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("/getAll")
	public List<Categories> getAll(){
		return categoryService.getAll();
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<HttpStatus> create(@Valid @RequestBody CategoryDTO categoryDTO) {
		if (categoryDTO != null) {
			categoryService.createOrUpdate(categoryDTO);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	@PutMapping("/update")
	public ResponseEntity<HttpStatus> update(@Valid @RequestBody CategoryDTO categoryDTO) {
		if (categoryDTO != null) {
			categoryService.createOrUpdate(categoryDTO);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteAllowanceType(@PathVariable("id") Integer id) {
		categoryService.delete(id);
	    return ResponseEntity.ok().build();
	  }
	@GetMapping("/new")
	public ResponseEntity<CategoryDTO> newForm() {

		CategoryDTO newEntity = new CategoryDTO();

		return ResponseEntity.ok(newEntity);
	}
	@GetMapping("edit/{id}")
	public ResponseEntity<Object> getOne(@PathVariable Integer id) {
		CategoryDTO categoryDTO = categoryService.getOne(id);
		if (categoryDTO != null) {
			return ResponseEntity.ok(categoryDTO);
		} else {
//	    	Locale currentLocale = LocaleContextHolder.getLocale();
//	    	System.out.println("Ngôn ngữ hiện tại: " + currentLocale);

			// Lấy tin nhắn từ message.properties dựa trên ngôn ngữ hiện tại
			String errorMessage = messagesource.getMessage("NotFound.category.category", null,
					LocaleContextHolder.getLocale());
			// Ném ResponseStatusException với mã trạng thái 404 và thông báo lỗi
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
		}
	}
	
	
}
