package com.example.hanghaegg.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

	INVALID_TOKEN (HttpStatus.BAD_REQUEST, "유효하지 않는 Token 입니다"),
	ISSUED_ACCESS_TOKEN (HttpStatus.OK, "엑세스 토큰이 발급되었습니다"),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
