package com.zerobase.commu.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommuException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    // 스택트레이스 제거
    @Override
    public synchronized Throwable fillInStackTrace() {
	return this;
    }

    public CommuException(ErrorCode errorCode) {
	this.errorCode = errorCode;
	this.message = errorCode.getMessage();
    }

}
