package com.example.hanghaegg.domain.matching.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class BoardDto {

	private String title;

	private String content;

	private String img;

	private Member member;

	public BoardDto(Board board){
		this.title = board.getTitle();
		this.content = board.getContent();
		this.img = board.getImg();
		this.member = board.getMember();
	}
}
