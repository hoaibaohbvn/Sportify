package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Fields;

public interface FieldDAO extends JpaRepository<Fields, Integer>{

}
