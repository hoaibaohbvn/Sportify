package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Contacts;

public interface ContactDAO extends JpaRepository<Contacts, Integer>{
	
}
