package com.example.hanghaegg.domain.chat.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaegg.domain.matching.entity.Board;
import com.example.hanghaegg.domain.matching.repository.BoardRepository;
import com.example.hanghaegg.domain.member.entity.Member;
import com.example.hanghaegg.domain.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	private final BoardRepository boardRepository;

	@GetMapping("/{boardId}")
	public void getChat(HttpServletRequest request, @PathVariable Long boardId, @AuthenticationPrincipal User user) {
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
		);

		HttpSession session = request.getSession();
		System.out.println("chat param id 값 출력 : "  + user);

		if (user.getUsername().equals(board.getMember().getNickname())) {
			String name = "master";
			session.setAttribute("sessionId", name);
		} else {
			String name = "guest" + session.toString().substring(session.toString().indexOf("@"));
			session.setAttribute("sessionId", name);
		}

		log.info("@ChatController, getChat()");
	}

	@GetMapping("/master")
	public String enterChatAsMaster(HttpServletRequest request) {

		log.info("@ChatController, getChat()");
		return "/chat";
	}
}
