package localhost.modellibrary.api.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryModel {

	@EqualsAndHashCode.Include
	private Integer id;

	@Size(min = 1, max = 128)
	@NotBlank
	private String name;

	@JsonProperty(access = Access.READ_ONLY)
	private boolean root;

	private CategoryModel parentCategory;
}
