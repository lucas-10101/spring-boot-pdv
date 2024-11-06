package localhost.api.products.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import localhost.api.products.entities.Category;
import localhost.api.products.services.CategoryService;
import localhost.commonslibrary.api.security.Authorities;
import localhost.modellibrary.api.products.CategoryModel;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CategoryRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EasyRandom easyRandom;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CategoryService categoryService;

	@Test
	@DisplayName("Test save method to create category")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_CATEGORIES })
	public void whenCreateCategory_shoudReturnCreated() throws Exception {

		CategoryModel model = easyRandom.nextObject(CategoryModel.class);
		model.setId(null);
		model.setParentCategory(null);

		String payload = objectMapper.writeValueAsString(model);

		mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test save method to update category")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_CATEGORIES })
	public void whenUpdateCategory_shouReturnOk() throws Exception {

		CategoryModel category = easyRandom.nextObject(CategoryModel.class);
		category.setId(null);
		category.setParentCategory(null);

		String payload = objectMapper.writeValueAsString(category);

		String response = mvc.perform(post("/categories").content(payload).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();

		CategoryModel responseObject = objectMapper.readValue(response, CategoryModel.class);

		category.setId(responseObject.getId());
		payload = objectMapper.writeValueAsString(category);

		mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test category listing")
	@WithMockUser(username = "mockuser", authorities = { Authorities.ProductsApi.MANAGE_CATEGORIES })
	public void whenGettingCategory_thenReturnTheCategory() throws Exception {

		Category category = easyRandom.nextObject(Category.class);
		category.setId(null);
		category.setParentCategory(null);
		categoryService.save(category);

		Integer targetId = category.getId();

		mvc.perform(get("/categories/" + targetId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(targetId));
	}

	@Test
	@DisplayName("Testing dening when saving without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenSaveWithNoPermission_shouReturnForbidden() throws Exception {

		CategoryModel model = easyRandom.nextObject(CategoryModel.class);
		model.setId(null);
		model.setParentCategory(null);

		String payload = objectMapper.writeValueAsString(model);

		mvc.perform(post("/categories").content(payload).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when listing without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenListingCategorysWithNoPermission_shouReturnForbidden() throws Exception {
		mvc.perform(get("/categories").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Testing dening when querying category without permission")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenGetCategoryWithNoPermission_shouReturnForbidden() throws Exception {
		mvc.perform(get("/categories/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}
}
