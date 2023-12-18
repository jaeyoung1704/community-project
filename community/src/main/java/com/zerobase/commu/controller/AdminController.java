package com.zerobase.commu.controller;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.zerobase.commu.dto.BoardDto;
import com.zerobase.commu.dto.PostDetail;
import com.zerobase.commu.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 게시판 생성
    @PostMapping("/board")
    public BoardDto createBoard(@RequestBody BoardDto boardDto) {

	return adminService.createBoard(boardDto);
    }

    // 게시판 수정
    @PatchMapping("/board")
    public BoardDto updateBoard(@RequestBody BoardDto boardDto) {
	return adminService.updateBoard(boardDto);
    }

    // 게시판 삭제
    @DeleteMapping("/board")
    public BoardDto deleteBoard(@RequestParam("boardNo") Long boardNo) {
	return adminService.deleteBoard(boardNo);
    }

    // 게시글 생성
    @PostMapping("/post")
    public PostDetail createPost(@RequestBody PostDetail postDetail,
				 HttpServletRequest request) {

	return adminService.createPost(postDetail, request);
    }

    // 게시글 수정
    @PatchMapping("/post")
    public PostDetail updatePost(@RequestBody PostDetail postDetail) {
	return adminService.updatePost(postDetail);
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    public PostDetail deletePost(@RequestParam("postNo") Long postNo) {
	return adminService.deletePost(postNo);
    }

}
