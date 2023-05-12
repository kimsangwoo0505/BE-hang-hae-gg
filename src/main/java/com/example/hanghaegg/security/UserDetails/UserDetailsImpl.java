package com.example.hanghaegg.security.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.hanghaegg.domain.member.entity.Member;

public class UserDetailsImpl implements UserDetails {

	private final Member member;
	private final String userName;

	public UserDetailsImpl(Member member, String userName) {
		this.member = member;
		this.userName = userName;
	}

	public Member getMember() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		String authority = "USER";
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);

		return authorities;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
