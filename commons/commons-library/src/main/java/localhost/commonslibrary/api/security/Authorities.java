package localhost.commonslibrary.api.security;

public abstract class Authorities {

	public static final String ADMIN = "ADMIN";

	public static final String LOGIN = "LOGIN";

	public abstract static class ProductsApi {

		public static final String MANAGE_PRODUCTS = "MANAGE_PRODUCTS";
		public static final String READ_PRODUCTS = "READ_PRODUCTS";
	}
}
