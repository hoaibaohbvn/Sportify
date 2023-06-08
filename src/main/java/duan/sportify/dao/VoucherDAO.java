package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Voucher;

public interface VoucherDAO extends JpaRepository<Voucher, String>{

}
