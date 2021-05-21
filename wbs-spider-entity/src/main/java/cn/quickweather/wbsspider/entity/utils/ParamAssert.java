package cn.quickweather.wbsspider.entity.utils;

import cn.quickweather.wbsspider.entity.enums.ErrorEnum;
import cn.quickweather.wbsspider.entity.exp.BizException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by maweihao on 5/20/21
 */
public class ParamAssert {

    public static void nonNull(Object o) {
        nonNull(o, ErrorEnum.INVALID_PARAM.getErrorMsg());
    }

    public static void nonNull(Object o, String msg) {
        if (o == null) {
            throw new BizException(ErrorEnum.INVALID_PARAM, msg);
        }
    }

    public static void nonBlank(CharSequence s) {
        nonBlank(s, ErrorEnum.INVALID_PARAM.getErrorMsg());
    }

    public static void nonBlank(CharSequence s, String msg) {
        nonNull(s, msg);
        if (StringUtils.isEmpty(s)) {
            throw new BizException(ErrorEnum.INVALID_PARAM);
        }
    }

    public static void nonEmpty(Collection c) {
        nonEmpty(c, ErrorEnum.INVALID_PARAM.getErrorMsg());
    }

    public static void nonEmpty(Collection c, String msg) {
        nonNull(c, msg);
        if (CollectionUtils.isEmpty(c)) {
            throw new BizException(ErrorEnum.INVALID_PARAM);
        }
    }
}
