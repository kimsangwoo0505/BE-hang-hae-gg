package com.example.hanghaegg.security.UserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Member member = memberRepository.findByUserName(userName)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

		return new UserDetailsImpl(member, member.getUserName());
	}
}
