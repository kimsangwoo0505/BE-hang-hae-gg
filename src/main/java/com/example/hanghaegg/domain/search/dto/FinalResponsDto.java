package com.example.hanghaegg.domain.search.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "set")
public class FinalResponsDto<T>{
	private HttpStatus status;
	private String message;
	private T data;

	public static <T> FinalResponsDto<T> setSuccess(String message, T data){
		return FinalResponsDto.set(HttpStatus.OK, message, data);
	}

	public static <T> FinalResponsDto<T> setBadRequest(String message, T data){
		return FinalResponsDto.set(HttpStatus.BAD_REQUEST, message, data);
	}

}
