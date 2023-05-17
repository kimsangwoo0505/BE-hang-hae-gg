package com.example.hanghaegg.domain.member.dto;

import com.example.hanghaegg.exception.ValidEmail;
import com.example.hanghaegg.exception.ValidPassword;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignUpDto {
	@ValidEmail
	private String email;

	@ValidPassword
	private String password;

	private String nickname;
}
