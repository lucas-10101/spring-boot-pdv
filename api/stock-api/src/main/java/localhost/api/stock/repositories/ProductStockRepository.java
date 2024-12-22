package localhost.api.stock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import localhost.api.stock.entities.ProductStock;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Integer> {

}
