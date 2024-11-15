package localhost.discoveryserver.eureka.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

	@Value(value = "${app.security.username}")
	private String username;

	@Value(value = "${app.security.password}")
	private String password;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username == null || password == null) {
			throw new IllegalStateException("username or password not provided, can't authenticate");
		}

		return new UserDetails() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getUsername() {
				return username;
			}

			@Override
			public String getPassword() {
				return "{noop}" + password;
			}

			@Override
			public Collection<GrantedAuthority> getAuthorities() {
				return new ArrayList<>(0);
			}
		};
	}

}
