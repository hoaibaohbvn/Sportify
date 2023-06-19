package duan.sportify.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import duan.sportify.entities.Field;



public interface FieldDAO extends JpaRepository<Field, Integer>{
	
}
