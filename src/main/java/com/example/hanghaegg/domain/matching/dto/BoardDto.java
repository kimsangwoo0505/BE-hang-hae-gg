package com.example.hanghaegg.domain.matching.dto;

import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class BoardDto {

	private long boardId;

	private String title;

	private String content;

	private String img;

	private Long memberId;

	private String memberName;

	private BoardDto(Long boardId, String title, String content, String img, Long memberId, String memberName) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.img = img;
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public static BoardDto of(Long boardId, String title, String content, String img, Long memberId, String memberName){
		return new BoardDto(boardId, title, content, img, memberId, memberName);
	}

	public static Board toEntity(BoardRequest request, Member member, String img) {
		return Board.of(
			request.getTitle(),
			request.getContent(),
			member,
			img
		);
	}
}
