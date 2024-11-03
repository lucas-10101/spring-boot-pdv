package localhost.api.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import localhost.api.products.entities.Product;
import localhost.api.products.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	/**
	 * Create or update as specified in {@link CrudRepository} save method
	 * 
	 * @param product product to save
	 * @return the persisted product
	 */
	public Product save(Product product) {
		return repository.save(product);
	}
}
