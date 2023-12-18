package com.zerobase.commu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zerobase.commu.exception.CommuException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    // 인터셉터에서 예외발생시 핸들러가 처리하기 힘드므로 이곳으로 보낸후 예외 던짐
    @GetMapping("/errorPage")
    public String Error(HttpServletRequest request) {
	log.error("에러페이지");
	CommuException e = (CommuException) request.getAttribute("exception");
	throw e;
    }

}
