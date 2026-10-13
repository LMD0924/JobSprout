package com.jobsprout.common.exception;

import com.jobsprout.common.result.ResultCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;
    public BusinessException(String message) { super(message); this.code=ResultCode.BAD_REQUEST.getCode(); }
    public BusinessException(ResultCode code) { super(code.getMessage()); this.code=code.getCode(); }
}
