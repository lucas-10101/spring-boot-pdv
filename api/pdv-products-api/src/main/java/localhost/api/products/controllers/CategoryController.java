package localhost.api.products.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import localhost.api.products.entities.Category;
import localhost.api.products.services.CategoryService;
import localhost.modellibrary.api.exceptions.ResourceNotFoundException;
import localhost.modellibrary.api.products.CategoryModel;

@RestController
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

	@Autowired
	private CategoryService service;

	@Autowired
	private ModelMapper mapper;

	@PostMapping
	public ResponseEntity<CategoryModel> save(@Valid @RequestBody CategoryModel categoryModel) {

		Category category = mapper.map(categoryModel, Category.class);
		category = this.service.save(category);

		var shoudReturnCreated = categoryModel.getId() == null;

		return ResponseEntity.status(shoudReturnCreated ? HttpStatus.CREATED : HttpStatus.OK).body(mapper.map(category, CategoryModel.class));
	}

	@GetMapping
	public ResponseEntity<PagedModel<CategoryModel>> listCategories(Pageable page) {

		var modelPage = new PagedModel<>(service.getPaged(page).map(entity -> mapper.map(entity, CategoryModel.class)));

		return ResponseEntity.ok(modelPage);
	}

	@GetMapping(path = "/{categoryId}")
	public ResponseEntity<CategoryModel> getCategory(@RequestBody Integer categoryId) {
		var category = this.service.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException());

		return ResponseEntity.ok(mapper.map(category, CategoryModel.class));
	}

	@DeleteMapping(path = "/{categoryId}")
	public ResponseEntity<Void> delete(@PathVariable Integer categoryId) {
		this.service.deleteCategory(categoryId);

		return ResponseEntity.ok(null);
	}
}
