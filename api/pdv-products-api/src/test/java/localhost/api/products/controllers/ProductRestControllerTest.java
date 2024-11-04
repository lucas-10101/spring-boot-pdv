package localhost.api.products.controllers;

import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
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

import localhost.commonslibrary.api.security.Authorities;
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
	@DisplayName("Test missing authorization on save")
	@WithMockUser(username = "mockuser", authorities = {})
	public void whenMissingAuthority_theDenyAccess() throws Exception {

		ProductModel stub = easyRandom.nextObject(ProductModel.class);

		String payload = objectMapper.writeValueAsString(stub);

		mvc.perform(post("/products").content(payload).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());

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
	@DisplayName("Test if products listage return an page of results")
	public void whenListingProducts_thenReturnAnPage() throws Exception {

		mvc.perform(get("/producs?page=0&order=name,asc"));

	}
}
