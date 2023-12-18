package com.zerobase.commu.service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.commu.dto.BoardDto;
import com.zerobase.commu.dto.MemberDto;
import com.zerobase.commu.dto.PostDetail;
import com.zerobase.commu.entity.Board;
import com.zerobase.commu.entity.Post;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.exception.CommuException;
import com.zerobase.commu.repo.BoardRepo;
import com.zerobase.commu.repo.PostRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final PostRepo postRepo;
    private final BoardRepo boardRepo;
    private final MemberService memberService;

    // 게시판 생성
    public BoardDto createBoard(BoardDto board) {
	// 게시판번호 자동 생성
	board.setBoardNo(null);
	return boardRepo.save(board.toEntity()).toDto();
    }

    // 게시판 수정
    public BoardDto updateBoard(BoardDto board) {
	// 잘못된 게시판번호 입력시 에러 발생
	if (boardRepo.findById(board.getBoardNo()).isEmpty())
	    throw new CommuException(ErrorCode.NO_SUCH_BOARD);
	return boardRepo.save(board.toEntity()).toDto();
    }

    // 게시판 삭제
    public BoardDto deleteBoard(Long boardNo) {
	// 잘못된 게시판번호 입력시 에러 발생
	Board board = boardRepo.findById(boardNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_BOARD));
	boardRepo.delete(board);
	return board.toDto();
    }

    // 게시글 생성
    public PostDetail createPost(PostDetail post, HttpServletRequest request) {
	// 게시판번호 자동 생성
	post.setPostNo(null);
	// 조회수는 0으로 생성
	post.setHitsAll(0l);
	post.setHitsDay(0l);
	post.setHitsWeek(0l);
	// 작성자는 세션에서 로그인아이디를 가져와서 입력
	MemberDto writer = memberService.getLoginUserFromSession(request);
	post.setWriter(writer);
	// 관리자가 생성하는 게시글은 항상 공지글
	post.setNotice(true);
	return postRepo.save(post.toEntity()).toDetail();
    }

    // 게시판 수정. 관리자는 게시글의 모든 필드 수정 가능
    public PostDetail updatePost(PostDetail post) {
	// 잘못된 게시판번호 입력시 에러 발생
	if (postRepo.findById(post.getPostNo()).isEmpty())
	    throw new CommuException(ErrorCode.NO_SUCH_POST);
	return postRepo.save(post.toEntity()).toDetail();
    }

    // 게시판 삭제
    public PostDetail deletePost(Long boardNo) {
	// 잘못된 게시판번호 입력시 에러 발생
	Post post = postRepo.findById(boardNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_BOARD));
	postRepo.delete(post);
	return post.toDetail();
    }

}
