package com.jobsprout.common.exception;

import com.jobsprout.common.result.Result;
import com.jobsprout.common.result.ResultCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

/** 统一处理参数、业务及系统异常。 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) { return Result.fail(e.getCode(), e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) { return Result.fail(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldErrors().stream().map(x -> x.getField()+"："+x.getDefaultMessage()).collect(Collectors.joining("；"))); }
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) { return Result.fail(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldErrors().stream().map(x -> x.getField()+"："+x.getDefaultMessage()).collect(Collectors.joining("；"))); }
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) { return Result.fail(ResultCode.BAD_REQUEST, e.getMessage()); }
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) { return Result.fail(ResultCode.INTERNAL_ERROR); }
}
