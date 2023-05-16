package com.example.hanghaegg.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchErrorCode implements ErrorCode {

	RIOT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "라이엇서버 접속에 실패하였습니다");



	private final HttpStatus httpStatus;
	private final String message;
}
