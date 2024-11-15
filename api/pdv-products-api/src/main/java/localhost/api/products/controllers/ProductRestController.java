package localhost.api.products.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import localhost.api.products.entities.Product;
import localhost.api.products.services.ProductService;
import localhost.commonslibrary.api.security.Authorities;
import localhost.modellibrary.api.exceptions.ResourceNotFoundException;
import localhost.modellibrary.api.products.CategoryModel;
import localhost.modellibrary.api.products.ProductModel;
import localhost.modellibrary.api.products.PropertyModel;
import localhost.modellibrary.validationgroups.CreateOrUpdate;

@RestController
@RequestMapping(path = "/products", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
public class ProductRestController {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductService service;

	@PostMapping
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<ProductModel> save(@Validated(value = CreateOrUpdate.class) @RequestBody ProductModel productModel) {
		Product product = mapper.map(productModel, Product.class);
		product = service.save(product);

		var shoudReturnCreated = productModel.getId() == null;

		return ResponseEntity.status(shoudReturnCreated ? HttpStatus.CREATED : HttpStatus.OK).body(mapper.map(product, ProductModel.class));
	}

	@GetMapping
	@Secured(Authorities.ProductsApi.READ_PRODUCTS)
	public ResponseEntity<PagedModel<ProductModel>> listProducts(Pageable page) {
		var result = new PagedModel<>(this.service.getPaged(page).map(e -> mapper.map(e, ProductModel.class)));
		return ResponseEntity.ok(result);
	}

	@GetMapping(path = "/{productId}")
	@Secured(Authorities.ProductsApi.READ_PRODUCTS)
	public ResponseEntity<ProductModel> getProduct(@PathVariable Integer productId) {

		var entity = this.service.findById(productId).orElseThrow(new ResourceNotFoundException());

		var product = mapper.map(entity, ProductModel.class);
		return ResponseEntity.ok(product);
	}

	@PutMapping(path = "/{productId}/disable")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> disableProduct(@PathVariable Integer productId) {
		this.service.disableById(productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PutMapping(path = "/{productId}/enable")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> enableProduct(@PathVariable Integer productId) {
		this.service.enableById(productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PutMapping(path = "/{productId}/add-category/{categoryId}")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> addCategoryToProduct(@PathVariable Integer productId, @PathVariable Integer categoryId) {
		this.service.addCategoryToProductCategory(categoryId, productId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}

	@PutMapping(path = "/{productId}/remove-category/{categoryId}")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> removeCategoryToProduct(@PathVariable Integer productId, @PathVariable Integer categoryId) {
		this.service.removeCategoryFromProduct(categoryId, productId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
	}

	@GetMapping(path = "/{productId}/categories")
	@Secured(Authorities.ProductsApi.READ_PRODUCTS)
	public ResponseEntity<Collection<CategoryModel>> getProductCategories(@PathVariable Integer productId) {
		var mapped = this.service.getProductCategories(productId).stream().map(e -> mapper.map(e, CategoryModel.class)).collect(Collectors.toList());

		return ResponseEntity.ok(mapped);
	}

	@PutMapping(path = "/{productId}/save-property")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> saveProductProperty(@PathVariable Integer productId, @Valid @RequestBody PropertyModel model) {
		this.service.saveProductProperty(productId, model.getName(), model.getDescription());
		return ResponseEntity.ok(null);
	}

	@PutMapping(path = "/{productId}/remove-property")
	@Secured(Authorities.ProductsApi.MANAGE_PRODUCTS)
	public ResponseEntity<Void> removeProductProperty(@PathVariable Integer productId, @RequestParam(required = true, name = "property") String keyName) {
		this.service.removeProductProperty(productId, keyName);
		return ResponseEntity.ok(null);
	}

	@GetMapping(path = "/{productId}/properties")
	@Secured(Authorities.ProductsApi.READ_PRODUCTS)
	public ResponseEntity<Collection<PropertyModel>> getProperties(@PathVariable Integer productId) {

		var content = this.service.getProductProperties(productId).stream().map(e -> mapper.map(e, PropertyModel.class)).collect(Collectors.toList());

		return ResponseEntity.ok(content);
	}
}
