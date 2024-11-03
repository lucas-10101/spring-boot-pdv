package localhost.api.products.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import localhost.api.products.entities.Product;
import localhost.api.products.services.ProductService;
import localhost.modellibrary.api.products.ProductModel;
import localhost.modellibrary.validationgroups.CreateOrUpdate;

@RestController
@RequestMapping(path = "/products", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProductRestController {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductService service;

	@PostMapping
	public ResponseEntity<ProductModel> save(@Validated(value = CreateOrUpdate.class) @RequestBody ProductModel productModel) {

		Product product = mapper.map(productModel, Product.class);
		product = service.save(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(product, ProductModel.class));
	}

	@GetMapping
	public ResponseEntity<Object> listProducts() {
		return null;
	}

	@GetMapping(path = "/{productId}")
	public ResponseEntity<Object> getProduct(@PathVariable Integer productId) {
		return null;
	}

}
