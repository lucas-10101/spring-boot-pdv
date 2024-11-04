package localhost.api.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import localhost.api.products.entities.Product;
import localhost.api.products.repositories.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	@Autowired
	private ProductRepository repository;

	/**
	 * Create or update as specified in {@link CrudRepository} save method
	 * 
	 * @param product product to save
	 * @return the persisted product
	 */
	@Transactional
	public Product save(Product product) {
		return repository.save(product);
	}

	public Page<Product> getPaged(Pageable page) {
		return repository.findAll(page);
	}

	public Product findById(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public boolean disableById(Integer id) {
		return this.repository.updateActiveStatusById(id, false) > 0;
	}

	@Transactional
	public boolean enableById(Integer id) {
		return this.repository.updateActiveStatusById(id, true) > 0;
	}
}
