package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Voucher;


@SuppressWarnings("unused")
public interface VoucherService {
	List<Voucher> findAll();

	Voucher create(Voucher voucher);

	Voucher update(Voucher voucher);

	void delete(String id);
	
	Voucher findById(String id);

	List<Voucher> findByVoucherId(String voucherid);
}
