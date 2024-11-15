package localhost.api.products.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import jakarta.transaction.Transactional;
import localhost.api.products.entities.Category;
import localhost.api.products.services.CategoryService;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CategoryServiceTest {

	@Autowired
	private CategoryService service;

	@Autowired
	private EasyRandom easyRandom;

	@Test
	@DisplayName("Test category creation")
	public void whenCreatingRootCategory_theCreate() {

		Category entity = easyRandom.nextObject(Category.class);
		entity.setId(null);
		entity.setParentCategory(null);

		entity = this.service.save(entity);

		assertNotNull(entity.getId());
	}

	@Test
	@DisplayName("Test root category creation")
	public void whenCreatingRootCategory_thenSetAsRoot() {

		Category entity = easyRandom.nextObject(Category.class);
		entity.setId(null);
		entity.setParentCategory(null);

		entity = this.service.save(entity);

		assertTrue(entity.isRoot());
	}

	@Test
	@DisplayName("Test child category creation")
	public void whenCreatingChildCategory_thenSetAsChild() {

		Category root = easyRandom.nextObject(Category.class);
		root.setId(null);
		root.setParentCategory(null);

		root = this.service.save(root);

		Category child = easyRandom.nextObject(Category.class);
		child.setId(null);
		child.setParentCategory(root);

		child = this.service.save(child);

		assertFalse(child.isRoot());
	}

	@Test
	@DisplayName("Test getPaged method service")
	public void whenRequestingPage_thenReturnPage() {

		PageRequest pageRequest = PageRequest.of(0, 20);

		var result = this.service.getPaged(pageRequest);

		assertInstanceOf(Page.class, result);

	}

	@Test
	@DisplayName("Test findById method")
	public void whenFindingById_thenReturnObjectById() {

		Category category = easyRandom.nextObject(Category.class);
		category.setId(null);
		category.setParentCategory(null);

		category = this.service.save(category);
		Integer id = category.getId();

		Category queryResult = this.service.findById(id).orElse(null);

		assertEquals(category, queryResult);
	}

	@Test
	@DisplayName("Testing delete method rules for root nodes")
	@Transactional
	public void whenDeletingRoot_thenMakeChildRoot() {

		Category root = easyRandom.nextObject(Category.class);
		root.setId(null);
		root.setParentCategory(null);

		root = this.service.save(root);

		Category child = easyRandom.nextObject(Category.class);
		child.setId(null);
		child.setParentCategory(root);

		child = this.service.save(child);

		this.service.deleteCategory(root.getId());

		child = this.service.findById(child.getId()).orElse(null);

		assertTrue(child.isRoot());
	}

	@Test
	@DisplayName("Testing delete method rules for child nodes")
	@Transactional
	public void whenDeletingChild_thenUpdanteNextNodeLevel() {

		Category root = easyRandom.nextObject(Category.class);
		root.setId(null);
		root.setParentCategory(null);

		root = this.service.save(root);

		Category child = easyRandom.nextObject(Category.class);
		child.setId(null);
		child.setParentCategory(root);

		child = this.service.save(child);

		Category childLv2 = easyRandom.nextObject(Category.class);
		childLv2.setId(null);
		childLv2.setParentCategory(child);

		childLv2 = this.service.save(childLv2);
		assertNotEquals(childLv2.getParentCategory(), root);

		this.service.deleteCategory(child.getId());

		childLv2 = this.service.findById(childLv2.getId()).orElse(null);
		assertEquals(childLv2.getParentCategory(), root);
	}
}
