package com.jobsprout.common.exception;

import com.jobsprout.common.result.Result;
import com.jobsprout.common.result.ResultCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/** 统一处理参数、业务及系统异常。 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 业务异常：只打 warn 日志，不打堆栈。 */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException exception) {
        log.warn("业务异常：code={}, message={}", exception.getCode(), exception.getMessage());
        return Result.fail(exception.getCode(), exception.getMessage());
    }

    /** 参数校验异常：打 warn 日志。 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + "：" + error.getDefaultMessage())
                .collect(Collectors.joining("；"));
        log.warn("参数校验失败：{}", message);
        return Result.fail(ResultCode.BAD_REQUEST, message);
    }

    /** 表单绑定异常：打 warn 日志。 */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + "：" + error.getDefaultMessage())
                .collect(Collectors.joining("；"));
        log.warn("表单绑定失败：{}", message);
        return Result.fail(ResultCode.BAD_REQUEST, message);
    }

    /** 约束违反异常：打 warn 日志。 */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException exception) {
        log.warn("约束违反：{}", exception.getMessage());
        return Result.fail(ResultCode.BAD_REQUEST, exception.getMessage());
    }

    /** 兜底异常：打 error 日志并输出完整堆栈，方便排查问题。 */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception exception) {
        log.error("系统异常：", exception);
        return Result.fail(ResultCode.INTERNAL_ERROR);
    }
}
