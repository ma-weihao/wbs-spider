package cn.quickweather.wbsspider.entity.exp;

import cn.quickweather.wbsspider.entity.enums.ErrorEnum;

/**
 * Created by maweihao on 5/20/21
 */
public class BizException extends RuntimeException {

    private String errorCode;
    private ErrorEnum errorEnum;

    public BizException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(ErrorEnum errorEnum, String msg) {
        super(msg);
        this.errorEnum = errorEnum;
        this.errorCode = errorEnum.name();
    }

    public BizException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMsg());
        this.errorEnum = errorEnum;
        this.errorCode = errorEnum.name();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }
}