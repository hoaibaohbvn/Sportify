package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import duan.sportify.entities.Eventweb;

public interface EventDAO extends JpaRepository<Eventweb, Integer>{

}
