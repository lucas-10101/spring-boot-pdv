package localhost.api.products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import localhost.api.products.entities.Product;
import localhost.api.products.services.ProductService;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ProductServiceTest {

	@Autowired
	private ProductService service;

	@Autowired
	private EasyRandom easyRandom;

	@Test
	@DisplayName("Check product create service")
	public void testCreateNewProduct() {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);

		product = service.save(product);

		assertNotNull(product.getId());
	}

	@Test
	@DisplayName("Get Page request")
	public void whenGetPaged_thenReturnPage() {
		Pageable page = PageRequest.of(0, 20, Sort.by(Direction.ASC, "id"));

		assertInstanceOf(Page.class, service.getPaged(page));
	}

	@Test
	@DisplayName("Return null on non existing id")
	public void whenFindByNonExistingId_theReturnNull() {
		assertNull(service.findById(Integer.MAX_VALUE));
	}

	@Test
	@DisplayName("Test find by id method")
	public void givenId_whenFindById_thenReturnProduct() {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);

		product = service.save(product);

		assertEquals(product.getId(), service.findById(product.getId()).getId());

	}

	@Test
	@DisplayName("Test disable product must change active to false")
	public void whenDisableById_thenActiveMustBeFalse() {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);
		product.setActive(true);

		product = service.save(product);
		service.disableById(product.getId());

		product = service.findById(product.getId());

		assertFalse(product.isActive());
	}

	@Test
	@DisplayName("Test enable product must change active to false")
	public void whenEnableById_thenActiveMustBeTrue() {

		Product product = easyRandom.nextObject(Product.class);
		product.setId(null);
		product.setActive(false);

		product = service.save(product);
		service.enableById(product.getId());

		product = service.findById(product.getId());

		assertTrue(product.isActive());
	}
}
