package com.zerobase.commu.service;

import java.net.http.HttpRequest;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zerobase.commu.dto.LoginUser;
import com.zerobase.commu.dto.MemberDto;
import com.zerobase.commu.entity.Member;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.exception.CommuException;
import com.zerobase.commu.repo.MemberRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepo memberRepo;
    private final PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    // 아이디 중복체크
    public boolean isIdAvailable(String id) {
	return !memberRepo.findById(id).isPresent();
    }

    // 회원가입
    public MemberDto signUp(MemberDto member) {
	// 아이디 중복 체크
	if (!isIdAvailable(member.getId()))
	    throw new CommuException(ErrorCode.ID_ALREADY_EXISTS);
	// 비밀번호 암호화
	member.setPw(pwEncoder.encode(member.getPw()));
	// 회원 정보 저장
	return memberRepo.save(member.toEntity()).toDto();
    }

    // 로그인
    public LoginUser login(MemberDto member, HttpServletRequest request) {

	// 아이디 미발견시 에러 발생
	Member userFromDB = memberRepo.findById(member.getId())
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_USER));
	// 비밀번호 불일치시 에러 발생
	if (!pwEncoder.matches(member.getPw(), userFromDB.getPw()))
	    throw new CommuException(ErrorCode.PASSWORD_MISMATCH);
	// 세션이 있으면 가져오고 없으면 새로 생성
	HttpSession session = request.getSession(true);
	// 세션 30분간 유지
	session.setMaxInactiveInterval(1800);
	// 로그인 세션 생성
	LoginUser loginUser = new LoginUser(userFromDB.getId(), userFromDB.isAdmin());
	session.setAttribute("loginUser", loginUser);
	return loginUser;
    }

    // 세션에서 로그인 정보(아이디,권한만) 가져오기
    public LoginUser getLoginIdFromSession(HttpServletRequest request) {
	// 세션이 있으면 가져오고 없으면 세션만료 에러페이지
	HttpSession session = request.getSession(false);
	if (session == null)
	    throw new CommuException(ErrorCode.SESSSION_EXPIRED);
	LoginUser loginId = (LoginUser) session.getAttribute("loginUser");
	// 세션 있어도 로그인안돼있으면 에러페이지
	if (loginId == null)
	    throw new CommuException(ErrorCode.SESSSION_EXPIRED);
	return loginId;
    }

    // 세션에서 로그인유저 모든 정보 가져오기
    public MemberDto getLoginUserFromSession(HttpServletRequest request) {
	// id로 유저repo에서 검색
	LoginUser loginId = getLoginIdFromSession(request);
	MemberDto user = memberRepo.findById(loginId.getId())
	    .orElseThrow(() -> new CommuException(ErrorCode.NO_SUCH_USER))
	    .toDto();
	// 비밀번호는 어차피 암호화돼있으므로 안가져옴
	user.setPw(null);

	return user;
    }

}
