package localhost.commonslibrary.api.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class JwtAuthenticationConverterImpl extends JwtAuthenticationConverter {

	public JwtAuthenticationConverterImpl() {

		JwtGrantedAuthoritiesConverter appSpecific = new JwtGrantedAuthoritiesConverter();
		appSpecific.setAuthoritiesClaimName("app_scopes");
		appSpecific.setAuthorityPrefix("");
		appSpecific.setAuthoritiesClaimDelimiter("");

		this.setJwtGrantedAuthoritiesConverter(appSpecific);
	}

}
