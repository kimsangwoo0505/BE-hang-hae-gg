package com.example.hanghaegg.domain.matching.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hanghaegg.domain.matching.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("select b from Board b left join fetch b.member")
	List<Board> findAllWithMember();
}
