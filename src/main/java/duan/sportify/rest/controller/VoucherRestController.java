package duan.sportify.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

import duan.sportify.GlobalExceptionHandler;

import duan.sportify.dao.VoucherDAO;
import duan.sportify.entities.Sporttype;
import duan.sportify.entities.Voucher;

import duan.sportify.utils.ErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/vouchers/")
public class VoucherRestController {
	@Autowired
	MessageSource messagesource;
	@Autowired
	VoucherDAO voucherDAO;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
		return GlobalExceptionHandler.handleValidationException(ex);
	}
	@GetMapping("getAll")
	public ResponseEntity<List<Voucher>> getAll(){
		return ResponseEntity.ok(voucherDAO.findAll());
	}
	@GetMapping("fillActive")
	public ResponseEntity<List<Voucher>> fillActive(){
		return ResponseEntity.ok(voucherDAO.fillActive());
	}
	@GetMapping("fillInActive")
	public ResponseEntity<List<Voucher>> fillInActive(){
		return ResponseEntity.ok(voucherDAO.fillInActive());
	}
	@GetMapping("fillWillActive")
	public ResponseEntity<List<Voucher>> fillWillActive(){
		return ResponseEntity.ok(voucherDAO.fillWillActive());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Voucher> getOne(@PathVariable("id") String id) {
		if(!voucherDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(voucherDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Voucher> create(@Valid @RequestBody Voucher voucher) {
	    if (voucher.getVoucherid() != null && voucherDAO.existsById(voucher.getVoucherid())) {
	        return ResponseEntity.status(1000).build();
	    }else if(voucher.getVoucherid() == null && !voucherDAO.existsById(voucher.getVoucherid())) {
	    	return ResponseEntity.badRequest().build();
	    }else {
	    	voucherDAO.save(voucher);
		    return ResponseEntity.ok(voucher);
	    }
	    
	}
	@PutMapping("update/{id}")
	public ResponseEntity<Voucher> update(@PathVariable("id") String id, @Valid @RequestBody Voucher voucher) {
		if(!voucherDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		voucherDAO.save(voucher);
		return ResponseEntity.ok(voucher);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		if(!voucherDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		voucherDAO.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
