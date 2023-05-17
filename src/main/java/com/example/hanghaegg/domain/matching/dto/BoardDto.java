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

	public BoardDto(Board board){
		this.boardId = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.img = board.getImg();
		this.memberId = board.getMember().getId();
		this.memberName = board.getMember().getNickname();
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
