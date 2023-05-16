package com.example.hanghaegg.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchErrorCode implements ErrorCode {

	IOException_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "입출력 작업 오류가 발생했습니다"),
	InterruptedException_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "쓰레드가 실행 중에 다른 쓰레드에 의해 중단됐습니다"),
	URISyntaxException(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 URI 구문을 사용했습니다");


	private final HttpStatus httpStatus;
	private final String message;
}
