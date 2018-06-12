package cn.wolfcode.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by leo on 2018/3/1.
 */
@Getter
@Setter
public class WSResponseVO implements Serializable {

    /**
     * 默认成功状态码（0 表示成功，非 0 表示失败）
     */
    public static final int DEFAULT_SUCCESS_CODE = 0;
    /**
     * 默认失败状态码：请求失败
     */
    public static final int DEFAULT_FALIED_CODE = 5000;
    /**
     * 参数异常
     */
    public static final int INVALID_PARAM_CODE = 4000;
    /**
     * 默认响应成功消息
     */
    public static final String DEFAULT_SUCCESS_MSG = "请求成功";
    /**
     * 默认响应失败消息
     */
    public static final String DEFAULT_FALIED_MSG = "请求失败";

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    public WSResponseVO() {
        this(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, null);
    }

    public WSResponseVO(Object data) {
        this(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, data);
    }

    public WSResponseVO(int code, String msg) {
        this(code, msg, null);
    }

    public WSResponseVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
