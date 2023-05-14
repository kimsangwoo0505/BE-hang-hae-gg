package com.example.hanghaegg.domain.matching.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequest {
	private String title;
	private String content;
	private MultipartFile boardFile; //FE -> BE (controller) 로 파일 담는 용도
	private String originalFileName; //원본 파일 이름
	private String storedFileName; //서버 저장용 파일 이름
	private int fileAttached; //파일 첨부 여부 (첨부 1, 미첨부 0)

}
