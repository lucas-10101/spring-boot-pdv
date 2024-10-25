package localhost.api.products.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterChainConfiguration {

	@Bean
	@Order(0)
	@Profile("dev")
	protected SecurityFilterChain h2SsecurityFilterChain(HttpSecurity http) throws Exception {

		http.securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"));
		http.authorizeHttpRequests(requests -> {
			requests.anyRequest().permitAll();
		});
		http.headers(headers -> {
			headers.frameOptions(frameOptions -> frameOptions.sameOrigin());
		});

		http.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(requests -> {
			requests.anyRequest().fullyAuthenticated();
		});
		http.headers(headers -> {
			headers.frameOptions(frameOptions -> frameOptions.sameOrigin());
		});
		http.sessionManagement(sessions -> {
			sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});

		http.rememberMe(rememberMe -> rememberMe.disable());
		http.csrf(Customizer.withDefaults());

		http.oauth2ResourceServer(resourceServer -> {
			resourceServer.jwt(Customizer.withDefaults());
		});

		return http.build();
	}
}
