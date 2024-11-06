package localhost.modellibrary.api.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryModel {

	@EqualsAndHashCode.Include
	private Integer id;

	@Size(min = 1, max = 128)
	@NotNull
	private String name;

	@JsonProperty(access = Access.READ_ONLY)
	private boolean root;

	@Valid
	private CategoryModel parentCategory;
}
