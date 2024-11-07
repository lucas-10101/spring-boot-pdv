package localhost.api.products.controllers;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import localhost.api.products.entities.Category;
import localhost.api.products.entities.Product;
import localhost.api.products.services.CategoryService;
import localhost.api.products.services.ProductService;
import localhost.commonslibrary.api.security.Authorities;
import localhost.modellibrary.api.products.CategoryModel;
import localhost.modellibrary.api.products.ProductModel;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ProductRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EasyRandom easyRandom;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Test
	@DisplayName("Test the save controller method with product creation")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenCreateProduct_thenReturnCreated() throws Exception {

		ProductModel stub = easyRandom.nextObject(ProductModel.class);
		stub.setId(null);

		String payload = objectMapper.writeValueAsString(stub);

		mvc.perform(post("/products").content(payload).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists());

	}

	@Test
	@DisplayName("Test the save controller method with product update")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenUpdatingProduct_thenUpdateFields() throws Exception {

		ProductModel stub = easyRandom.nextObject(ProductModel.class);
		stub.setId(null);

		String payload = objectMapper.writeValueAsString(stub);

		String bodyContent = mvc.perform(post("/products").content(payload).contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getContentAsString();

		ProductModel returned = objectMapper.readValue(bodyContent, ProductModel.class);
		returned.setName("--changed--");

		stub.setId(returned.getId());

		payload = objectMapper.writeValueAsString(returned);
		mvc.perform(post("/products").content(payload).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", not(eq(stub.getName()))));

	}

	@Test
	@DisplayName("Test product listing")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenGettingProduct_thenReturnTheProduct() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);
		productService.save(product);

		Integer targetId = product.getId();

		mvc.perform(get("/products/" + targetId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(targetId));
	}

	@Test
	@DisplayName("Disable product endpoint test")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void testDisableProduct() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);
		product.setActive(true);
		productService.save(product);

		Integer targetId = product.getId();

		mvc.perform(put("/products/" + targetId + "/disable").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		assertFalse(productService.findById(targetId).orElse(null).isActive());
	}

	@Test
	@DisplayName("Enable product endpoint test")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void testEnableProduct() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);
		product.setActive(false);
		productService.save(product);

		Integer targetId = product.getId();

		mvc.perform(put("/products/" + targetId + "/enable").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		assertTrue(productService.findById(targetId).orElse(null).isActive());
	}

	@Test
	@DisplayName("Testing dening when saving without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenSaveWithNoPermission_shouReturnForbidden() throws Exception {

		ProductModel stub = easyRandom.nextObject(ProductModel.class);
		stub.setId(null);

		String payload = objectMapper.writeValueAsString(stub);

		mvc.perform(post("/products").content(payload).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when listing without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenListingProductsWithNoPermission_shouReturnForbidden() throws Exception {
		mvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when querying product without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenGetProductWithNoPermission_shouReturnForbidden() throws Exception {
		mvc.perform(get("/products/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when disabling product without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenDisablingProductWhitoutPermission_shoudReturnForbidden() throws Exception {
		mvc.perform(put("/products/1/enable").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when querying product without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenEnablingProductWhitoutPermission_shoudReturnForbidden() throws Exception {
		mvc.perform(put("/products/1/disable").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Test adding categories to product")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenAddingCategoryToProduct_shoudAddToLink() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);

		product = productService.save(product);

		Category category = easyRandom.nextObject(Category.class);
		category.setId(null);
		category.setParentCategory(null);

		category = categoryService.save(category);

		mvc.perform(put("/products/%d/add-category/%d".formatted(product.getId(), category.getId())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());

		var linked = this.productService.getProductCategories(product);

		assertTrue(linked.contains(category));

	}

	@Test
	@DisplayName("Test removing categories to product")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenRemovingCategoryToProduct_shoudRemoveFromLink() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);

		product = productService.save(product);

		Category category = easyRandom.nextObject(Category.class);
		category.setId(null);
		category.setParentCategory(null);

		category = categoryService.save(category);

		this.productService.addCategoryToProduct(category, product);

		mvc.perform(put("/products/%d/remove-category/%d".formatted(product.getId(), category.getId())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());

		var linked = this.productService.getProductCategories(product);

		assertFalse(linked.contains(category));

	}

	@Test
	@DisplayName("Test listing categories")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_PRODUCTS })
	public void whenListingCategoriesFromProduct_shoudReturnAnCollection() throws Exception {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);

		product = productService.save(product);

		Category category = easyRandom.nextObject(Category.class);
		category.setId(null);
		category.setParentCategory(null);

		category = categoryService.save(category);

		this.productService.addCategoryToProduct(category, product);

		String response = mvc.perform(get("/products/%d/categories".formatted(product.getId())).contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse().getContentAsString();
		Collection<CategoryModel> responseMapped = objectMapper.readValue(response, new TypeReference<Collection<CategoryModel>>() {
		});

		assertFalse(responseMapped.isEmpty());

	}
}
