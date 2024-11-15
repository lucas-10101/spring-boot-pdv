package localhost.api.products.configuration.beans;

import org.jeasy.random.EasyRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyRandomBean {

	@Bean
	protected EasyRandom easyRandom() {
		return new EasyRandom();
	}
}
