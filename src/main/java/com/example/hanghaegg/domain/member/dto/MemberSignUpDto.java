package com.example.hanghaegg.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignUpDto {

	private String email;
	private String password;
	private String nickname;
}
