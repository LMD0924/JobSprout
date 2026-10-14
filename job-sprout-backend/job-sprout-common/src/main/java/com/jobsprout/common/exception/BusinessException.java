package com.jobsprout.common.exception;

import com.jobsprout.common.result.ResultCode;
import lombok.Getter;

/** 可预期的业务异常。 */
@Getter
public class BusinessException extends RuntimeException {
    private final int code;
    public BusinessException(String message) { this(ResultCode.BAD_REQUEST.getCode(), message); }
    public BusinessException(ResultCode resultCode) { this(resultCode.getCode(), resultCode.getMessage()); }
    public BusinessException(ResultCode resultCode, String message) { this(resultCode.getCode(), message); }
    public BusinessException(int code, String message) { super(message); this.code=code; }
}
