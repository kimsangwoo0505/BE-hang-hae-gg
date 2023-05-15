package com.example.hanghaegg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.hanghaegg.security.jwt.JwtService;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Swagger2Config {
	private final JwtService jwtService;
	@Bean
	public OpenAPI openAPI() {
		Info info = new Info()
			.version("v1.0.0")
			.title("hanghaegg")
			.description("Api Description");

		String token_header = jwtService.getAccessHeader();

		SecurityRequirement securityRequirement = new SecurityRequirement().addList(token_header);

		Components components = new Components()
			.addSecuritySchemes(token_header, new SecurityScheme()
				.name(token_header)
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.HEADER));

		return new OpenAPI()
			.info(info)
			.addSecurityItem(securityRequirement)
			.components(components);
	}
}
