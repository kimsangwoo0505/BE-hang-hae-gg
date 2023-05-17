package com.example.hanghaegg.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
//ConstraintValidator 인터페이스는 Bean Validation API의 일부로, 개발자가 커스텀 제약 조건을 정의할 수 있게 해주는 인터페이스
private static final String PASSWORD_PATTERN = "^[^\\s]{4,8}$";


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.matches(PASSWORD_PATTERN);
	}// 조건이 만족되지 않으면 유효하지 않다고 판단하고 false를 반환
}
