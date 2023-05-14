package com.example.hanghaegg.domain.matching.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hanghaegg.domain.matching.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
