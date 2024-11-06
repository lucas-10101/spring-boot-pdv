package localhost.api.products.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import localhost.api.products.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Collection<Category> findAllByParentCategory(Category parentCategory);
}
