package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Sporttype;

public interface SportTypeDAO extends JpaRepository<Sporttype, String>{

}
