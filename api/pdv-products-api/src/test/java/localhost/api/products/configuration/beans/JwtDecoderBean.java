package localhost.api.products.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@Configuration
public class JwtDecoderBean {

	@Bean
	protected JwtDecoder nullJwtDecoder() {
		return new JwtDecoder() {

			@Override
			public Jwt decode(String token) throws JwtException {
				return null;
			}
		};
	}
}
