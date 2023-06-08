package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Events;

public interface EventDAO extends JpaRepository<Events, Integer>{

}
