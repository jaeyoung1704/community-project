package com.zerobase.commu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.zerobase.commu.dto.*;
import com.zerobase.commu.service.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    // 회원 가입
    @PostMapping("/signUp")
    public MemberDto signUp(@RequestBody MemberDto member) {
	return memberService.signUp(member);
    }

    // 로그인
    @PostMapping("/logIn")
    public LoginUser logIn(@RequestBody MemberDto member, HttpServletRequest request) {
	return memberService.login(member, request);
    }

    // 로그아웃
    @PostMapping("/logOut")
    public void logOut(HttpServletRequest request) {
	log.info("로그아웃");
	memberService.logOut(request);
    }

    // 내정보 조회
    @GetMapping("/myPage")
    public MemberDto myPage(HttpServletRequest request) {
	return memberService.getLoginUserFromSession(request);
    }

    // 내가 쓴 글
    @GetMapping("/myPost")
    public List<PostDetail> myPost(HttpServletRequest request) {
	return postService.findByWriter(request);
    }

    // 내 댓글
    @GetMapping("/myComment")
    public List<CommentDto> myComment(HttpServletRequest request) {
	String writer = memberService.getLoginIdFromSession(request).getId();
	return commentService.CommnetByWriter(writer);
    }

}
