package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teams;

public interface TeamDAO extends JpaRepository<Teams, Integer>{

}
