package localhost.api.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import localhost.api.products.entities.ProductProperty;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductProperty, ProductProperty.Identity> {

}
