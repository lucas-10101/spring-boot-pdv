package localhost.modellibrary.api.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PropertyModel {

	@NotBlank
	@Size(min = 1, max = 128)
	@EqualsAndHashCode.Include
	private String name;

	@NotBlank
	@Size(min = 1, max = 256)
	private String description;
}
