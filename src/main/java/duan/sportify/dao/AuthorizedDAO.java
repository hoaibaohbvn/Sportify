package duan.sportify.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import duan.sportify.entities.Authorized;

public interface AuthorizedDAO extends JpaRepository<Authorized, Integer>{

}
