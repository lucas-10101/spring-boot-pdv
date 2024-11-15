package localhost.api.products.repositories;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import localhost.api.products.entities.Category;
import localhost.api.products.entities.Product;
import localhost.api.products.entities.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategory.Identity> {

	Collection<OnlyCategoryProjection> findAllByIdProduct(Product product);

	/**
	 * JPA Projection to retrieve only the category from id
	 */
	public static interface OnlyCategoryProjection {

		@Value("#{target.id.category}")
		Category getCategory();
	}
}
