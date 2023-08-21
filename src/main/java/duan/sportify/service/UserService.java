package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Users;


@SuppressWarnings("unused")
public interface UserService {
	List<Users> findAll();

	Users create(Users users);

	Users update(Users users);

	void delete(String id);
	
	Users findById(String id);

	Object findByUsername(String username);
	
	
}
