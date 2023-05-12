package com.example.hanghaegg.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaegg.domain.member.dto.MemberDto;
import com.example.hanghaegg.domain.member.dto.MemberRequest;
import com.example.hanghaegg.domain.member.dto.MemberResponse;
import com.example.hanghaegg.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/user/signup")
	public ResponseEntity<MemberResponse> signup(@RequestBody final MemberRequest memberRequest) {

		memberService.signup(MemberDto.from(memberRequest));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new MemberResponse("회원가입에 성공했습니다"));
	}

	@PostMapping("/user/login")
	public ResponseEntity<MemberResponse> login(
		@RequestBody final MemberRequest memberRequest,
		final HttpServletResponse response) {

		memberService.login(MemberDto.from(memberRequest), response);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(new MemberResponse("로그인에 성공했습니다"));
	}
}
