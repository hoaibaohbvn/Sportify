package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Roles;


@SuppressWarnings("unused")
public interface RoleService {
	List<Roles> findAll();

	Roles create(Roles roles);

	Roles update(Roles roles);

	void delete(String id);
	
	Roles findById(String id);
}
