package com.zerobase.commu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zerobase.commu.dto.*;
import com.zerobase.commu.entity.Comment;
import com.zerobase.commu.exception.CommuException;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.repo.CommentRepo;
import com.zerobase.commu.repo.PostRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final MemberService memberService;
    private final PostRepo postRepo;

    // 게시글 댓글 조회
    public List<CommentDto> CommentByPost(Long postNo) {
	return commentRepo.findAllByPost_PostNo(postNo)
	    .stream()
	    .map(x -> x.toDto())
	    .collect(Collectors.toList());
    }

    // 댓글 작성자로 조회
    public List<CommentDto> CommnetByWriter(String writer) {
	return commentRepo.findAllByWriter_Id(writer)
	    .stream()
	    .map(x -> x.toDto())
	    .collect(Collectors.toList());
    }

    // 댓글 작성
    public CommentDto writeComment(Long postNo, CommentDto comment,
				   HttpServletRequest request) {
	comment.setCommentNo(null);
	// 입력받은 게시글번호가 잘못됐으면 오류 반환
	PostDetail post = postRepo.findById(postNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_POST))
	    .toDetail();
	// 작성자는 세션에서 로그인아이디를 가져와서 입력
	MemberDto writer = memberService.getLoginUserFromSession(request);
	comment.setWriter(writer);
	comment.setPost(post);
	return commentRepo.save(comment.toEntity()).toDto();
    }

    // 댓글 수정
    public CommentDto updateComment(Long commentNo, CommentDto commentChanged,
				    HttpServletRequest request) {
	// 세션에서 로그인정보 가져오기 (미로그인 에러는 memberservice에서 처리)
	MemberDto member = memberService.getLoginUserFromSession(request);

	// 입력받은 댓글 번호가 잘못됐으면 오류 반환
	CommentDto commentOriginal = commentRepo.findById(commentNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_COMMENT))
	    .toDto();

	log.info("로그인:{}, 작성자:{}", member.getId(), commentOriginal.getWriter().getId());
	// 해당 게시글 작성자가 본인이 아니면 오류 반환
	if (!member.getId().equals(commentOriginal.getWriter().getId()))
	    throw new CommuException(ErrorCode.NO_AUTORITY);

	// 댓글 내용만 수정 가능
	commentOriginal.setContent(commentChanged.getContent());

	// 수정후 정보 리턴
	return commentRepo.save(commentOriginal.toEntity()).toDto();
    }

    // 댓글 삭제
    public CommentDto deleteComment(Long commentNo, HttpServletRequest request) {
	// 삭제할 댓글 번호 입력이 잘못됐으면 오류 반환
	Comment comment = commentRepo.findById(commentNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_COMMENT));

	// 세션에서 로그인정보 가져오기 (미로그인 에러는 memberservice에서 처리)
	MemberDto member = memberService.getLoginUserFromSession(request);

	// 해당 게시글 작성자가 본인이 아니면 오류 반환
	if (!member.getId().equals(comment.getWriter().getId()))
	    throw new CommuException(ErrorCode.NO_AUTORITY);

	// 삭제후 리턴
	commentRepo.delete(comment);
	return comment.toDto();
    }
}
