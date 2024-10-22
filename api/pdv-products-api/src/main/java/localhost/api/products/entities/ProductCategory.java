package localhost.api.products.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "product_categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductCategory {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private ProductCategory.Identity id;

	@Data
	@Embeddable
	public static class Identity implements Serializable {
		private static final long serialVersionUID = 1L;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "product_id", nullable = false, updatable = false)
		private Product product;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "category_id", nullable = false, updatable = false)
		private Category category;
	}
}
