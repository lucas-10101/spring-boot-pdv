package localhost.api.products.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false, updatable = false)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(length = 128, nullable = false)
	private String name;

	@Column(length = 512, nullable = false)
	private String description;

	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private boolean active;
}
