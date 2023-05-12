package com.example.hanghaegg.domain.member.dto;

import com.example.hanghaegg.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDto {

	private String userName;
	private String password;

	private MemberDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public static MemberDto from(MemberRequest request) {

		return new MemberDto(
			request.getUserName(),
			request.getPassword()
		);
	}

	public static Member toEntity(MemberDto dto) {

		return Member.of(
			dto.getUserName(),
			dto.getPassword()
		);
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}
