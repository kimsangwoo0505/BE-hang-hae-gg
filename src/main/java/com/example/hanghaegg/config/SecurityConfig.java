package com.example.hanghaegg.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.hanghaegg.security.jwt.JwtAuthenticationFilter;
import com.example.hanghaegg.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
			.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.cors()
			.and()
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/user/**").permitAll()
				// .antMatchers(HttpMethod.GET, "/boards/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			)
			.anonymous().disable()
			.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(){

		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:3000");
		config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		config.validateAllowCredentials();

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}
}
