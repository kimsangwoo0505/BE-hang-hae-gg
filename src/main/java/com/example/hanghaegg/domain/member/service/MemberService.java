package com.example.hanghaegg.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaegg.domain.member.constant.MemberRole;
import com.example.hanghaegg.domain.member.dto.MemberSignUpDto;
import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {

		if (memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()) {
			throw new Exception("이미 존재하는 이메일입니다.");
		}

		if (memberRepository.findByNickname(memberSignUpDto.getNickname()).isPresent()) {
			throw new Exception("이미 존재하는 닉네임입니다.");
		}

		Member member = Member.builder()
			.email(memberSignUpDto.getEmail())
			.password(memberSignUpDto.getPassword())
			.nickname(memberSignUpDto.getNickname())
			.role(MemberRole.USER)
			.build();

		member.passwordEncode(passwordEncoder);
		memberRepository.save(member);
	}
}
