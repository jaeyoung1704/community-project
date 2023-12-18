package com.zerobase.commu.service;

import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zerobase.commu.dto.BoardDto;
import com.zerobase.commu.dto.MemberDto;
import com.zerobase.commu.dto.PostDetail;
import com.zerobase.commu.dto.PostSimple;
import com.zerobase.commu.entity.Post;
import com.zerobase.commu.exception.CommuException;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.repo.BoardRepo;
import com.zerobase.commu.repo.PostRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final BoardRepo boardRepo;
    private final MemberService memberService;
    private final CommentService commentService;

    // 게시판 생성
    public BoardDto createBoard(BoardDto board) {
	return boardRepo.save(board.toEntity()).toDto();
    }

    // 특정 게시판의 게시글 전체 조회
    public List<PostSimple> postList(long boardNo, String sort) {

	// 항상 공지는 최우선조회, 그다음으론 일간 또는 주간 조회수 순으로 조회할지 정할수있음
	List<Post> postList = null;
	if (sort.equals("day"))
	    postList = postRepo.findAllByBoard_BoardNoOrderByIsNoticeDescHitsDayDesc(boardNo);
	else if (sort.equals("week"))
	    postList = postRepo.findAllByBoard_BoardNoOrderByIsNoticeDescHitsWeekDesc(boardNo);
	else // 기본순 조회
	    postList = postRepo.findAllByBoard_BoardNoOrderByIsNoticeDescPostNoDesc(boardNo);
	return postList.stream().map(x -> x.toSimple()).collect(Collectors.toList());
    }

    // 특정 게시글 조회
    @Transactional
    public PostDetail readPost(long postNo, HttpServletRequest request,
			       HttpServletResponse response) {
	// 게시글 번호로 게시글 가져오고 없으면 예외 반환
	Post post = postRepo.findById(postNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_POST));
	// 조회수 증가
	post.setHitsAll(post.getHitsAll() + 1);
	post.setHitsDay(post.getHitsDay() + 1);
	post.setHitsWeek(post.getHitsWeek() + 1);
	postRepo.save(post);

//	// 이미 본 게시글인지 조회
//	Cookie[] cookies = request.getCookies();
//	Cookie viewedPost = null;
//	if (cookies != null)
//	    for (Cookie cookie : cookies) {
//		if (cookie.getName().equals("viewedPost")) {
//		    log.info("발견");
//		    viewedPost = cookie;
//		}
//	    }
//	if (viewedPost == null) {
//	    viewedPost = new Cookie("viewedPost", "밸류");
//	    // 다음날 0시에 만료되는 쿠키
//	    LocalDateTime expires = LocalDateTime.now();
//	    expires = expires.plusDays(1);
//	    expires = expires.withHour(0);
//	    expires = expires.withMinute(0);
//	    expires = expires.withSecond(0);
//	    viewedPost.setAttribute("expires", expires.toString());
//	    viewedPost.setMaxAge(60 * 3600 * 24);
//	    response.addCookie(viewedPost);
//	}
//	log.info("쿠키이름:{}, 값:{}, maxage:{},속성들:{}", viewedPost.getName(),
//	    viewedPost.getValue(), viewedPost.getMaxAge(), viewedPost.getAttributes());
	// 댓글 조회
	PostDetail dto = post.toDetail();
	dto.setComments(commentService.CommentByPost(postNo));
	return dto;
    }

    // 게시글 작성
    public PostDetail writePost(long boardNo, PostDetail post, HttpServletRequest request) {
	// 입력받은 게시판 번호가 잘못됐으면 오류 반환
	BoardDto board = boardRepo.findById(boardNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_BOARD))
	    .toDto();
	post.setBoard(board);
	// 작성자는 세션에서 로그인아이디를 가져와서 입력
	MemberDto writer = memberService.getLoginUserFromSession(request);
	post.setBoard(board);
	post.setWriter(writer);
	post.setHitsAll(0l);
	post.setHitsDay(0l);
	post.setHitsWeek(0l);
	return postRepo.save(post.toEntity()).toDetail();
    }

    // 게시글 수정
    public PostDetail updatePost(long postNo, PostDetail postChanged,
				 HttpServletRequest request) {
	// 세션에서 로그인정보 가져오기 (미로그인 에러는 memberservice에서 처리)
	MemberDto member = memberService.getLoginUserFromSession(request);

	// 입력받은 게시글 번호가 잘못됐으면 오류 반환
	PostDetail postOriginal = postRepo.findById(postNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_POST))
	    .toDetail();

	// 해당 게시글 작성자가 본인이 아니면 오류 반환
	if (!member.getId().equals(postOriginal.getWriter().getId()))
	    throw new CommuException(ErrorCode.NO_AUTORITY);

	// 게시글 제목, 게시글 내용만 수정 가능
	postOriginal.setTitle(postChanged.getTitle());
	postOriginal.setContent(postChanged.getContent());

	// 수정후 정보 리턴
	return postRepo.save(postOriginal.toEntity()).toDetail();
    }

    // 게시글 삭제
    public PostDetail deletePost(Long boardNo, HttpServletRequest request) {
	// 삭제할 게시글 번호 입력이 잘못됐으면 오류 반환
	Post post = postRepo.findById(boardNo)
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_POST));

	// 세션에서 로그인정보 가져오기 (미로그인 에러는 memberservice에서 처리)
	MemberDto member = memberService.getLoginUserFromSession(request);

	// 해당 게시글 작성자가 본인이 아니면 오류 반환
	if (!member.getId().equals(post.getWriter().getId()))
	    throw new CommuException(ErrorCode.NO_AUTORITY);

	// 삭제후 리턴
	postRepo.delete(post);
	return post.toDetail();
    }

    // 작성자 아이디로 검색
    public List<PostDetail> findByWriter(HttpServletRequest request) {
	// 로그인 세션에서 아이디 받아오기
	String writer = memberService.getLoginIdFromSession(request).getId();

	// 작성자 아이디로 검색
	return postRepo.findAllByWriter_Id(writer)
	    .stream()
	    .map(x -> x.toDetail())
	    .collect(Collectors.toList());
    }

}
