package localhost.commonslibrary.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.Builder;;

public abstract class RoleHierarchyDefinition {

	@Bean
	protected RoleHierarchy getDefaultRoleHierarchy() {

		Builder hierarchyBuilder = RoleHierarchyImpl.withRolePrefix("");

		buildAdministrativeRoleHierarchy(hierarchyBuilder);
		buildProductsApiRoleHierarchy(hierarchyBuilder);

		return hierarchyBuilder.build();

	}

	/**
	 * Generate {@link Authorities.ADMIN} hierarchy.
	 * 
	 * @param builder The builder instance
	 * @return The hierarchy
	 */
	protected Builder buildAdministrativeRoleHierarchy(Builder hierarchyBuilder) {
		return hierarchyBuilder
				.role(Authorities.ADMIN).implies(Authorities.ProductsApi.MANAGE_PRODUCTS)
				.role(Authorities.ADMIN).implies(Authorities.ProductsApi.MANAGE_CATEGORIES);
	}

	/**
	 * Generate {@link Authorities.ProductsApi} roles hierarchy.
	 * 
	 * @param builder The builder instance
	 * @return The hierarchy
	 */
	protected Builder buildProductsApiRoleHierarchy(Builder hierarchyBuilder) {

		return hierarchyBuilder
				.role(Authorities.ProductsApi.MANAGE_PRODUCTS).implies(Authorities.ProductsApi.READ_PRODUCTS)
				.role(Authorities.ProductsApi.MANAGE_CATEGORIES).implies(Authorities.ProductsApi.READ_CATEGORIES);
	}
}
