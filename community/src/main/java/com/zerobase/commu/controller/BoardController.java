package com.zerobase.commu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.commu.dto.BoardDto;
import com.zerobase.commu.dto.CommentDto;
import com.zerobase.commu.dto.PostDetail;
import com.zerobase.commu.dto.PostSimple;
import com.zerobase.commu.service.BoardService;
import com.zerobase.commu.service.CommentService;
import com.zerobase.commu.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;

    // 게시판 목록 조회
    @GetMapping("/list")
    public List<BoardDto> readBoardList() {

	return boardService.boardList();
    }

    // 특정 게시판의 게시글 전체 조회
    @GetMapping("/{boardNo}")
    public List<PostSimple> readPostList(@PathVariable("boardNo") long boardNo,
					 @RequestParam("sort") String sort) {

	return postService.postList(boardNo, sort);
    }

    // 게시글 상세 조회
    @GetMapping("/post/{postNo}")
    public PostDetail readPostDetail(@PathVariable("postNo") long postNo) {

	return postService.readPost(postNo);
    }

    // 게시글 입력
    @PostMapping("/{boardNo}")
    public PostDetail writePost(@PathVariable("boardNo") long boardNo,
				@RequestBody PostDetail post, HttpServletRequest request) {

	return postService.writePost(boardNo, post, request);
    }

    // 게시글 수정
    @PatchMapping("/{postNo}")
    public PostDetail updatePost(@PathVariable("postNo") long postNo,
				 @RequestBody PostDetail post, HttpServletRequest request) {

	return postService.updatePost(postNo, post, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{postNo}")
    public PostDetail deletePost(@PathVariable("postNo") long postNo,
				 HttpServletRequest request) {

	return postService.deletePost(postNo, request);
    }

    // 댓글 입력
    @PostMapping("/comment/{postNo}")
    public CommentDto writeComment(@PathVariable("postNo") long postNo,
				   @RequestBody CommentDto comment,
				   HttpServletRequest request) {
	return commentService.writeComment(postNo, comment, request);
    }

    // 댓글 수정
    @PatchMapping("/comment/{commentNo}")
    public CommentDto updateComment(@PathVariable("commentNo") long commentNo,
				    @RequestBody CommentDto comment,
				    HttpServletRequest request) {
	return commentService.updateComment(commentNo, comment, request);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentNo}")
    public CommentDto deleteComment(@PathVariable("commentNo") long commentNo,
				    HttpServletRequest request) {
	return commentService.deleteComment(commentNo, request);
    }
}
