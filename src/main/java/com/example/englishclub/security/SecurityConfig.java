package com.example.englishclub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authz) -> authz
						.anyRequest().authenticated()
				)
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(withDefaults());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public UserDetailsManager users(DataSource dataSource) {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("select username, passwords, enabled from users where username=?");
		users.setAuthoritiesByUsernameQuery("select username, role from users where username=?");
		return users;
	}



	public Authentication getAuth(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

}





