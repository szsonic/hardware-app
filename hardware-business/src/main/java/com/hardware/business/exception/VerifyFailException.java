package com.hardware.business.exception;

/**
 * <br>
 * 数据校验失败异常,主要用于一些非空校验等。
 * @author sunzhongshuai
 */
public class VerifyFailException extends  Exception{

    public VerifyFailException(String message) {
        super(message);
    }
}
