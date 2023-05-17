package com.example.hanghaegg.domain.matching.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hanghaegg.domain.matching.dto.BoardDto;
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.matching.repository.BoardRepository;
import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;
import com.example.hanghaegg.exception.CommonErrorCode;
import com.example.hanghaegg.exception.MemberErrorCode;
import com.example.hanghaegg.exception.RestApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final AmazonS3Client amazonS3Client;
	private String S3Bucket = "board-img";

	@Transactional(readOnly = true)
	public List<BoardDto> getAllBoards() {

		List<Board> boards = boardRepository.findAllWithMember();
		List<BoardDto> boardDtos = new ArrayList<>();

		for(Board board : boards){
			boardDtos.add(new BoardDto(board));
		}

		return boardDtos;
	}

	@Transactional(readOnly = true)
	public BoardDto getBoard(final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);
		return new BoardDto(board);
	}

	@Transactional
	public void createBoard(final BoardRequest boardRequest, final MultipartFile file, User user) {

		String mail = user.getUsername();
		Optional<Member> member = memberRepository.findByEmail(mail);

		if(file.isEmpty()){
			throw new RestApiException(CommonErrorCode.INVALID_REQUEST_PARAMETER);

		}else{
			String imagePath = saveImg(file);
			Board board = BoardDto.toEntity(boardRequest, member.get(), imagePath);
			boardRepository.saveAndFlush(board);
		}
	}

	@Transactional
	public void deleteBoard(final Long boardId, String email) {

		Optional<Member> member = memberRepository.findByEmail(email);
		Board board = findBoardByIdOrElseThrow(boardId);

		if(!member.get().equals(board.getMember())){
			throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
		}

		deleteImg(board);
		boardRepository.delete(board);
	}

	private String saveImg (final MultipartFile file) {

		String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
		long size = file.getSize();
		ObjectMetadata objectMetaData = new ObjectMetadata();
		objectMetaData.setContentType(file.getContentType());
		objectMetaData.setContentLength(size);

		try {
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
		} catch (IOException e) {
			throw new RestApiException(CommonErrorCode.IO_EXCEPTION);
		}

		return amazonS3Client.getUrl(S3Bucket, fileName).toString();
	}

	private void deleteImg(final Board board) {

		String[] imgId = board.getImg().split("/");
		amazonS3Client.deleteObject(S3Bucket, imgId[imgId.length - 1]);
	}

	private Board findBoardByIdOrElseThrow(final Long boardId) {

		return boardRepository.findById(boardId).orElseThrow(
			() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
		);
	}
}
