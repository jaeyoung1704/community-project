package com.zerobase.commu.exception;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommuExceptionHandler {
    @ExceptionHandler(CommuException.class)
    @ResponseBody
    public CommuException handleCustomException(CommuException e)
	throws ServletException, IOException {
	log.error(e.getErrorCode().toString() + " " + e.getMessage());
	return e;
    }
}
