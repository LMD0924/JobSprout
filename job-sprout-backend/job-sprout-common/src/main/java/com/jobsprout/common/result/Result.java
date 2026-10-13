package com.jobsprout.common.result;

import lombok.Data;

@Data
public class Result<T> {
    private int code; private String message; private T data; private long timestamp;
    public Result() { this.timestamp = System.currentTimeMillis(); }
    public static <T> Result<T> success(T data) { Result<T> r = new Result<>(); r.code=ResultCode.SUCCESS.getCode(); r.message=ResultCode.SUCCESS.getMessage(); r.data=data; return r; }
    public static <T> Result<T> success() { return success(null); }
    public static <T> Result<T> fail(ResultCode code) { Result<T> r=new Result<>(); r.code=code.getCode(); r.message=code.getMessage(); return r; }
    public static <T> Result<T> fail(int code, String message) { Result<T> r=new Result<>(); r.code=code; r.message=message; return r; }
}
