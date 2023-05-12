package com.example.hanghaegg.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hanghaegg.exception.ErrorCode;
import com.example.hanghaegg.exception.ErrorResponse;
import com.example.hanghaegg.exception.TokenErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = jwtUtil.resolveToken(request);

		if(token != null) {
			if(!jwtUtil.validateToken(token)){
				jwtExceptionHandler(response, TokenErrorCode.INVALID_ACCESS_TOKEN);
				return;
			}
			Claims info = jwtUtil.getUserInfoFromToken(token);
			setAuthentication(info.getSubject());
		}
		filterChain.doFilter(request,response);
	}

	public void setAuthentication(String username) {

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = jwtUtil.createAuthentication(username);
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	public void jwtExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {

		response.setStatus(errorCode.getHttpStatus().value());
		response.setContentType("application/json");
		try {
			String json = new ObjectMapper()
				.writeValueAsString(new ErrorResponse(
					errorCode.name(),
					errorCode.getHttpStatus().toString(),
					errorCode.getMessage()
				));
			response.getWriter().write(json);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
