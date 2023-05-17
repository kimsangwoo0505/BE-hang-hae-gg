package com.example.hanghaegg.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaegg.domain.member.dto.MemberSignUpDto;
import com.example.hanghaegg.domain.member.service.MemberService;
import com.example.hanghaegg.exception.EmailValidator;
import com.example.hanghaegg.exception.MemberErrorCode;
import com.example.hanghaegg.exception.PasswordValidator;
import com.example.hanghaegg.exception.RestApiException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final PasswordValidator passwordValidator = new PasswordValidator();
	private final EmailValidator emailValidator=new EmailValidator();
	@PostMapping("/signup")
	public String signUp(@RequestBody MemberSignUpDto memberSignUpDto) throws Exception {
		memberService.signUp(memberSignUpDto);

		String email = memberSignUpDto.getEmail();
		String password = memberSignUpDto.getPassword();

		if (!emailValidator.isValid(email, null)) {
			throw new RestApiException(MemberErrorCode.EMAIL_ERROR1);
		}
		if (!passwordValidator.isValid(password, null)) {
			throw new RestApiException(MemberErrorCode.PASSWORD_ERROR1);
		}

		return "회원가입 성공";
	}

	@GetMapping("/jwtTest")
	public String jwtTest() {
		return "jwtTest 요청 성공";
	}
}
