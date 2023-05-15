package com.example.hanghaegg.domain.matching.service;

import java.io.IOException;
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
import com.example.hanghaegg.domain.matching.dto.BoardRequest;
import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.matching.repository.BoardRepository;
import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final AmazonS3Client amazonS3Client;
	private String S3Bucket = "board-img";

	// @Transactional(readOnly = true)
	// public List<BoardDto> searchBoards() {
	//
	// 	return boardRepository.findAllJoinFetch()
	// 		.stream()
	// 		.map(BoardListResponse::from)
	// 		.sorted(Comparator.comparing(BoardListResponse::getCreatedAt).reversed())
	// 		.collect(Collectors.toList());
	// }
	//
	// @Transactional(readOnly = true)
	// public BoardResponse searchBoard(final Long boardId) {
	//
	// 	Board board = findBoardByIdOrElseThrow(boardId);
	//
	// 	return BoardResponse.from(board);
	// }
	//
	// @Transactional(readOnly = true)
	// public List<BoardResponse> searchBoards(final String address) {
	//
	// 	return boardRepository.findByAddressJoinFetch(address)
	// 		.stream()
	// 		.map(BoardResponse::from)
	// 		.sorted(Comparator.comparing(BoardResponse::getCreatedAt).reversed())
	// 		.collect(Collectors.toList());
	// }

	@Transactional
	public void createBoard(final BoardRequest boardRequest, final MultipartFile file, User user) {

		String imagePath = saveImg(file);
		// boardDto.setMember(user);
		// boardRequest.setImg(imagePath);

		String mail = user.getUsername();
		Optional<Member> member = memberRepository.findByEmail(mail);
		Board board = new Board(boardRequest, member.get(), imagePath);
		boardRepository.saveAndFlush(board);
	}

	// @Transactional
	// public void updateBoard(final Member member, final Long boardId, final BoardDto boardDto, final MultipartFile file) {
	//
	// 	Board board = findBoardByIdOrElseThrow(boardId);
	//
	// 	throwIfNotOwner(board, member.getUsername());
	//
	// 	if (file == null) {
	// 		boardDto.setImg(board.getImg());
	// 	} else {
	// 		deleteImg(board);
	// 		boardDto.setImg(saveImg(file));
	// 	}
	//
	// 	board.updateBoard(boardDto.getTitle(), boardDto.getContent(), boardDto.getAddress(), boardDto.getImg());
	// }

	@Transactional
	public void deleteBoard(final Long boardId) {

		Board board = findBoardByIdOrElseThrow(boardId);
		// throwIfNotOwner(board, member.getUsername());
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
			// throw new RestApiException(CommonErrorCode.IO_EXCEPTION);
			throw new IllegalArgumentException("이미지 저장에 실패했습니다.");
		}

		return amazonS3Client.getUrl(S3Bucket, fileName).toString();
	}

	private void deleteImg(final Board board) {

		String[] imgId = board.getImg().split("/");
		amazonS3Client.deleteObject(S3Bucket, imgId[imgId.length - 1]);
	}

	private Board findBoardByIdOrElseThrow(final Long boardId) {

		return boardRepository.findById(boardId).orElseThrow(
			// () -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
			() -> new IllegalArgumentException("게시글을 찾을 수 없습니다.")
		);
	}

	// private void throwIfNotOwner(final Board board, final String loginUsername) {
	//
	// 	if (!board.getMember().getUsername().equals(loginUsername))
	// 		throw new RestApiException(MemberErrorCode.INACTIVE_MEMBER);
	// }
}
