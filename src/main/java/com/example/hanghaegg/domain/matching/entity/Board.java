package com.example.hanghaegg.domain.matching.entity;

import java.util.Optional;

import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BOARD_ID")
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@Column
	private int fileAttached;

	@Column
	private String fileName;

	private String img;

	public Board(BoardRequest boardRequest, Member member, int fileAttached, String img, String fileName) {
		this.title = boardRequest.getTitle();
		this.content = boardRequest.getContent();
		this.member = member;
		this.fileAttached = fileAttached;
		this.img = img;
		this.fileName = fileName;
	}

	//TODO: private 생성자 추가

	// public static Board of(String title, String content, String img) {
	// 	return new Board(title, content, img);
	// }
}
