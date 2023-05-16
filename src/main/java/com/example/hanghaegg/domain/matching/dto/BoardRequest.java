package com.example.hanghaegg.domain.matching.dto;

import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {

	private String title;

	private String content;

	public static Board toEntity(BoardRequest request, Member member, String img) {
		return Board.of(
			request.getTitle(),
			request.getContent(),
			member,
			img
		);
	}
}
