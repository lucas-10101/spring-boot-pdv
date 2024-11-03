package localhost.api.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import localhost.api.products.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
