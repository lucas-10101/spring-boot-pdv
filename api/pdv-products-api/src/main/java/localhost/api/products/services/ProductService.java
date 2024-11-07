package localhost.api.products.services;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import localhost.api.products.entities.Category;
import localhost.api.products.entities.Product;
import localhost.api.products.entities.ProductCategory;
import localhost.api.products.repositories.ProductCategoryRepository;
import localhost.api.products.repositories.ProductRepository;
import localhost.modellibrary.api.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private CategoryService categoryService;

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

	public Optional<Product> findById(Integer id) {
		return repository.findById(id);
	}

	@Transactional
	public boolean disableById(Integer id) {
		return this.repository.updateActiveStatusById(id, false) > 0;
	}

	@Transactional
	public boolean enableById(Integer id) {
		return this.repository.updateActiveStatusById(id, true) > 0;
	}

	@Transactional
	public void addCategoryToProductCategory(Integer categoryId, Integer productId) {

		var product = this.findById(productId).orElseThrow(new ResourceNotFoundException());
		var category = this.categoryService.findById(categoryId).orElseThrow(new ResourceNotFoundException());

		this.addCategoryToProduct(category, product);
	}

	@Transactional
	public void addCategoryToProduct(Category category, Product product) {

		var id = new ProductCategory.Identity();

		id.setProduct(product);
		id.setCategory(category);

		if (productCategoryRepository.existsById(id)) {
			return;
		}

		var productCategory = new ProductCategory();
		productCategory.setId(id);

		productCategoryRepository.save(productCategory);
	}

	@Transactional
	public void removeCategoryFromProduct(Integer categoryId, Integer productId) {

		var product = this.findById(productId).orElseThrow(new ResourceNotFoundException());
		var category = this.categoryService.findById(categoryId).orElseThrow(new ResourceNotFoundException());

		this.removeCategoryFromProduct(category, product);

	}

	@Transactional
	public void removeCategoryFromProduct(Category category, Product product) {

		var id = new ProductCategory.Identity();

		id.setProduct(product);
		id.setCategory(category);

		productCategoryRepository.deleteById(id);
	}

	public Collection<Category> getProductCategories(Integer productId) {

		var product = this.findById(productId).orElseThrow(new ResourceNotFoundException());

		return this.getProductCategories(product);
	}

	public Collection<Category> getProductCategories(Product product) {
		return productCategoryRepository.findAllByIdProduct(product)
				.stream()
				.map(r -> r.getCategory())
				.collect(Collectors.toList());
	}
}
