package com.example.hanghaegg.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaegg.domain.member.dto.MemberDto;
import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;
import com.example.hanghaegg.security.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(final MemberDto memberDto) {

		// throwIfExistOwner(memberRequest.getUsername(), memberRequest.getEmail());
		String password = passwordEncoder.encode(memberDto.getPassword());
		memberDto.updatePassword(password);

		Member member = MemberDto.toEntity(memberDto);
		memberRepository.save(member);
	}

	@Transactional
	public void login(final MemberDto memberDto, final HttpServletResponse response) {

		String userName = memberDto.getUserName();
		String password = memberDto.getPassword();

		Member member = memberRepository.findByUserName(userName).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 유저입니다")
		);
		// Member searchedMember = memberRepository.findByUsername(username).orElseThrow(
		// 	() -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND)
		// );
		//
		// if(!passwordEncoder.matches(password, searchedMember.getPassword())){
		// 	throw new RestApiException(MemberErrorCode.INVALID_PASSWORD);
		// }

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUserName()));
	}

	// private void throwIfExistOwner(String loginUsername) {
	//
	// 	Optional<Member> searchedMember = memberRepository.findByUsername(loginUsername);
	//
	// 	if (searchedMember.isPresent()) {
	// 		throw new RestApiException(MemberErrorCode.DUPLICATED_MEMBER);
	// 	}
	// }
}
