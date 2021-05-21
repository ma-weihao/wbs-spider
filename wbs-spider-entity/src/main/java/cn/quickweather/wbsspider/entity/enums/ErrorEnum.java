package cn.quickweather.wbsspider.entity.enums;

/**
 * Created by maweihao on 5/20/21
 */
public enum ErrorEnum {

    SUCCESS(0, "成功"),

    AUTHENTICATION_FAILED(101, "鉴权失败"),

    CLIENT_VERSION_LOW(102, "客户端版本号过低"),

    INVALID_PARAM(201, "入参非法"),

    RELY_SERVICE_ERROR(301, "依赖服务异常"),

    INTERNAL_ERROR(401, "系统内部异常"),

    FATAL_ERROR(901, "服务异常"),

    ;

    private final int errorNo;
    private final String errorMsg;

    ErrorEnum(int errorNo, String errorMsg) {
        this.errorNo = errorNo;
        this.errorMsg = errorMsg;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
