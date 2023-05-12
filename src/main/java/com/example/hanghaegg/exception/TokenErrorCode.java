package com.example.hanghaegg.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

	INVALID_ACCESS_TOKEN (HttpStatus.BAD_REQUEST, "유효하지 않는 Access token 입니다"),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
