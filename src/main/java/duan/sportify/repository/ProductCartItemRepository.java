package duan.sportify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import duan.sportify.entities.Orderdetails;

@Repository
public interface ProductCartItemRepository extends JpaRepository<Orderdetails, Integer>{

}
