package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;




public interface AuthorizedService {
	List<Authorized> findAll();

	Authorized create(Authorized authorized);

	Authorized update(Authorized authorized);

	void delete(Integer id);
	
	Authorized findById(Integer id);
}
