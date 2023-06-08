package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Users;

public interface UserDAO extends JpaRepository<Users, String>{

}
