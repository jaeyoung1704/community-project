package com.zerobase.commu.service;

import java.util.*;
import java.util.stream.Collectors;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.commu.dto.*;
import com.zerobase.commu.entity.*;
import com.zerobase.commu.repo.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;

//    private final PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    // 게시판 목록 조회
    public List<BoardDto> boardList() {
	List<Board> boardList = boardRepo.findAll();
	return boardList.stream().map(x -> x.toDto()).collect(Collectors.toList());
    }

}
