package com.example.hanghaegg.domain.matching.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hanghaegg.domain.matching.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
