package localhost.api.products.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import localhost.api.products.entities.Category;
import localhost.api.products.repositories.CategoryRepository;
import localhost.modellibrary.api.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional
	public Category save(Category category) {

		if (category.getParentCategory() == null) {
			category.setRoot(true);
		} else {
			var parent = this.categoryRepository.findById(category.getParentCategory().getId()).orElse(null);

			if (parent == null) {
				throw new IllegalArgumentException();
			}
			category.setRoot(false);
			category.setParentCategory(parent);
		}

		return this.categoryRepository.save(category);
	}

	public Page<Category> getPaged(Pageable page) {
		return this.categoryRepository.findAll(page);
	}

	public Optional<Category> findById(Integer id) {
		return this.categoryRepository.findById(id);
	}

	/**
	 * Delete the specified category, update child nodes with root or parent.
	 * 
	 * @param id The target for delete operate
	 */
	@Transactional
	public void deleteCategory(Integer id) {

		var category = this.categoryRepository.findById(id).orElseThrow(new ResourceNotFoundException());

		var subCategories = this.categoryRepository.findAllByParentCategory(category);

		subCategories.forEach(subCategory -> {
			subCategory.setRoot(category.isRoot());
			subCategory.setParentCategory(category.getParentCategory());
		});

		this.categoryRepository.saveAll(subCategories);
		this.categoryRepository.delete(category);
	}
}
