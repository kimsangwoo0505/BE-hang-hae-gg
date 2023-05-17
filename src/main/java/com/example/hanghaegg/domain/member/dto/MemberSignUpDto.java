package com.example.hanghaegg.domain.member.dto;

import com.example.hanghaegg.exception.ValidEmail;
import com.example.hanghaegg.exception.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignUpDto {
	@ValidEmail
	@NotBlank(message = "Email은 필수 값입니다.")
	private String email;

	@ValidPassword
	@NotBlank(message = "Password는 필수 값입니다.")
	private String password;

	@NotBlank(message = "Nickname은 필수 값입니다.")
	private String nickname;
}
