package com.zerobase.commu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//URL 입력돼있지 않은 코드는 이전페이지로, 입력돼있는 코드는 해당페이지로 redirect
public enum ErrorCode {
    // 아이디 미발견
    NO_SUCH_USER("해당 유저가 없습니다. 아이디와 비밀번호를 확인해주세요"),
    // 세션없거나 만료시 메인화면(로그인화면)으로 튕김
    SESSSION_EXPIRED("로그인 세션이 만료되었거나 로그인하지 않았습니다. 다시 로그인해주세요."),
    // 해당 게시판 없음
    NO_SUCH_BOARD("존재하지 않는 게시판입니다"),
    // 해당 게시글 없음
    NO_SUCH_POST("존재하지 않는 게시글입니다"),
    // 해당 댓글 없음
    NO_SUCH_COMMENT("존재하지 않는 댓글입니다"),
    // 아이디 이미 있음
    ID_ALREADY_EXISTS("이미 존재하는 아이디입니다. 다른 아이디로 시도해주세요"),
    // 패스워드 불일치
    PASSWORD_MISMATCH("올바르지 않은 비밀번호 입니다"),
    // 권한 없음
    NO_AUTORITY("해당 게시글/댓글을 수정/삭제할 권한이 없습니다");

    private final String message;
}
