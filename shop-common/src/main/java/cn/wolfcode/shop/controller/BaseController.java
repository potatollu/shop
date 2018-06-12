package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.vo.WSResponseVO;

public class BaseController {
    protected WSResponseVO success(){
        return success(null);
    }

    protected WSResponseVO success(Object data){
        return new WSResponseVO(data);
    }

    protected WSResponseVO failed(int code, String msg) {
        return failed(code, msg, null);
    }

    public WSResponseVO failed(int code, String msg, Object data) {
        return new WSResponseVO(code,msg,data);
    }
}
