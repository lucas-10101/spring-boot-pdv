package localhost.api.products.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false, updatable = false)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(length = 128, nullable = false)
	private String name;

	@Column(updatable = false, nullable = false)
	private boolean root;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "parent_category_id", updatable = false)
	private ProductCategory parentCategory;

}
