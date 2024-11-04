package localhost.api.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import localhost.api.products.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Modifying
	@Query("UPDATE Product p SET p.active = :newStatus WHERE p.id = :productId ")
	public int updateActiveStatusById(@Param("productId") Integer productId, @Param("newStatus") boolean newStatus);
}
