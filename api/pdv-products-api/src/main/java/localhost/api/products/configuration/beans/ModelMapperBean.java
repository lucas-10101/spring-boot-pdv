package localhost.api.products.configuration.beans;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import localhost.api.products.entities.ProductProperty;
import localhost.modellibrary.api.products.PropertyModel;

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

		mapper.addConverter(new Converter<PropertyModel, ProductProperty>() {
			@Override
			public ProductProperty convert(MappingContext<PropertyModel, ProductProperty> context) {

				PropertyModel model = context.getSource();

				var entityId = new ProductProperty.Identity();
				entityId.setName(model.getName());

				ProductProperty property = new ProductProperty();
				property.setId(entityId);
				property.setDescription(model.getDescription());

				return property;
			}
		});

		mapper.addConverter(new Converter<ProductProperty, PropertyModel>() {
			@Override
			public PropertyModel convert(MappingContext<ProductProperty, PropertyModel> context) {

				ProductProperty entity = context.getSource();

				PropertyModel model = new PropertyModel();

				model.setName(entity.getId().getName());
				model.setDescription(entity.getDescription());

				return model;
			}
		});

	}
}
