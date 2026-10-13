package com.jobsprout.common.exception;

import com.jobsprout.common.result.Result;
import com.jobsprout.common.result.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(BusinessException e) { return Result.fail(e.getCode(), e.getMessage()); }
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) { return Result.fail(ResultCode.INTERNAL_ERROR); }
}
