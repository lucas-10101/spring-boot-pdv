package localhost.api.products.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
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
@Table(name = "product_properties")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductProperty {

	@EmbeddedId
	@EqualsAndHashCode.Include
	private ProductProperty.Identity id;

	@Column(length = 256, nullable = false)
	private String description;

	@Data
	@Embeddable
	public static class Identity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(length = 128, nullable = false, updatable = false)
		private String name;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "product_id", nullable = false, updatable = false)
		private Product product;
	}
}
