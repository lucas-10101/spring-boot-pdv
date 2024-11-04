package localhost.modellibrary.api.products;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import localhost.modellibrary.validationgroups.Update;
import lombok.Data;

@Data
public class ProductModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Min(value = 1, groups = { Update.class })
	private Integer id;

	@Size(min = 1, max = 128)
	@NotNull
	private String name;

	@Size(min = 1, max = 512)
	@NotNull
	private String description;

	@DecimalMin(value = "0.00")
	@DecimalMax(value = "9999999999.99")
	@NotNull
	private BigDecimal price;

	@NotNull
	private boolean active;

}
