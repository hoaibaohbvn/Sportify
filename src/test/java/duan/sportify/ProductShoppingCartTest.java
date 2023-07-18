package duan.sportify;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import duan.sportify.entities.Orderdetails;
import duan.sportify.entities.Orders;
import duan.sportify.entities.Products;
import duan.sportify.repository.ProductCartItemRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductShoppingCartTest{
	@Autowired
	private ProductCartItemRepository cartRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void TestAddOneCartItem() {
		Orders order = entityManager.find(Orders.class, 1);
		Products product = entityManager.find(Products.class, 6);
		
		Orderdetails newOrder = new Orderdetails();
		newOrder.setOrders(order);
		newOrder.setProducts(product);
		newOrder.setPrice((double) 100000);
		newOrder.setQuantity(2);
		
		Orderdetails saveCart = cartRepo.save(newOrder);
		
		assertTrue(saveCart.getOrderdetailsid() > 0);
	}
}