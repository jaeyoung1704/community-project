package com.zerobase.commu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.commu.entity.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {


}
