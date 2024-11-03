package localhost.api.products.controllers.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

	@Order(Ordered.LOWEST_PRECEDENCE)
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> translateDefaults(Exception e) {
		return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(null);
	}
}
