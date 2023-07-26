package duan.sportify.service;

import java.util.List;

import duan.sportify.entities.Authorized;
import duan.sportify.entities.Teamdetails;


@SuppressWarnings("unused")
public interface TeamDetailService {
	List<Teamdetails> findAll();

	Teamdetails create(Teamdetails teamdetails);

	Teamdetails update(Teamdetails teamdetails);

	void delete(Integer id);
	
	Teamdetails findById(Integer id);
}
