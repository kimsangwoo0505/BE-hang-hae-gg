package com.example.hanghaegg.domain.matching.entity;

import com.example.hanghaegg.domain.matching.dto.BoardDto;
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

	@Column(nullable = false)
	private String img;

	private Board(String title, String content, Member member, String img) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.img = img;
	}

	public static Board of(String title, String content, Member member, String img) {
		return new Board(title, content, member, img);
	}
}
