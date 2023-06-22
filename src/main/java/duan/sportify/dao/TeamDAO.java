package duan.sportify.dao;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Teams;

public interface TeamDAO extends JpaRepository<Teams, Integer>{
}
