package com.example.hanghaegg.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchErrorCode implements ErrorCode {

	RIOT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "라이엇 서버에 문제가 발생했습니다"),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
