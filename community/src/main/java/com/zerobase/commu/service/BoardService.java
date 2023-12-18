package com.zerobase.commu.service;

import java.net.http.HttpRequest;
import java.util.*;
import java.util.stream.Collectors;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.commu.dto.*;
import com.zerobase.commu.entity.*;
import com.zerobase.commu.repo.*;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.exception.CommuException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final MemberRepo memberRepo;
    private final MemberService memberService;

//    private final PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    // 게시판 목록 조회
    public List<BoardDto> boardList() {
	List<Board> boardList = boardRepo.findAll();
	return boardList.stream().map(x -> x.toDto()).collect(Collectors.toList());
    }

}
