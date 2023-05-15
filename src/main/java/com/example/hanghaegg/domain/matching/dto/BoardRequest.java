package com.example.hanghaegg.domain.matching.dto;

import com.example.hanghaegg.domain.matching.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {

	private String title;
	private String content;

	private String img;


	public static Board toEntity(BoardRequest request) {
		return Board.of(
			request.getTitle(),
			request.getContent(),
			request.getImg()
		);
	}
}
