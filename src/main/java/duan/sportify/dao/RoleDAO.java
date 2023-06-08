package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Roles;

public interface RoleDAO extends JpaRepository<Roles, String>{

}
