package com.hardware.business.exception;

/**
 * <br>
 * 请求第三方接口异常
 * @author sunzhongshuai
 */
public class ThirdPartyRequestException extends Exception{

    public ThirdPartyRequestException(String message) {
        super(message);
    }
}
