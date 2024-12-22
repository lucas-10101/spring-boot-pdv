package localhost.api.sales.configuration.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperBean {

	@Bean
	@Scope("singleton")
	protected ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		registerCustomConverters(mapper);

		return mapper;
	}

	/**
	 * Pre-initialization custom deep mappings for controller usability inside the
	 * application
	 * 
	 * @param mapper
	 */
	private void registerCustomConverters(ModelMapper mapper) {

	}
}
