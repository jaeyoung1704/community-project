package com.zerobase.commu.intercepter;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import com.zerobase.commu.dto.LoginUser;
import com.zerobase.commu.exception.ErrorCode;
import com.zerobase.commu.exception.CommuException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//세션이 없거나 만료시 로그인 화면으로 인터셉트(예외 핸들러 사용)
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			     Object handler)
	throws ServletException, IOException {
	HttpSession session = request.getSession(false);
	log.info("로그인 인터셉터");
	// 인터셉터에서 예외발생시 핸들러가 처리하기 힘드므로 에러페이지로 보낸후 예외 던짐
	if (session == null)
	    return notLoggedIn(request, response);

	log.info("인터셉터2");
	LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
	if (loginUser == null)
	    return notLoggedIn(request, response);
	log.info("url:{}", request.getRequestURI());
	log.info("로그인 확인:{}", loginUser.toString());
	return true;
    }

    private static boolean notLoggedIn(HttpServletRequest request,
				       HttpServletResponse response)
	throws ServletException, IOException {
	request.setAttribute("exception", new CommuException(ErrorCode.SESSSION_EXPIRED));
	request.getRequestDispatcher("/errorPage").forward(request, response);
	return false;
    }

}
