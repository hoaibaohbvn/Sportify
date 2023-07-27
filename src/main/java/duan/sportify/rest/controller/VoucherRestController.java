package duan.sportify.rest.controller;

import java.util.List;

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
	public ResponseEntity<List<Voucher>> getAll(Model model){
		return ResponseEntity.ok(voucherDAO.findAll());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<Voucher> getOne(@PathVariable("id") String id) {
		if(!voucherDAO.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(voucherDAO.findById(id).get());
	}
	@PostMapping("create")
	public ResponseEntity<Voucher> create(@RequestBody Voucher voucher) {
		if(!voucherDAO.existsById(voucher.getVoucherid())) {
			
			return ResponseEntity.badRequest().build();
		}
		voucherDAO.save(voucher);
		return ResponseEntity.ok(voucher);
	}
	@PutMapping("update")
	public ResponseEntity<Voucher> update(@PathVariable("id") String id, @RequestBody Voucher voucher) {
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
