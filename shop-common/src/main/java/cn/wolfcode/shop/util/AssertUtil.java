package cn.wolfcode.shop.util;

import cn.wolfcode.shop.exception.WSException;

public class AssertUtil {

    public static void isNull(Object obj,String msg){
        boolean flag = false;
        if (obj == null){
            flag = true;
        }
        if (obj instanceof String && "".equals((String)((String) obj).trim())){
            flag = true;
        }
        faile(flag,msg);
    }

    public static void faile(boolean flag, String msg) {
        if (flag){
            throw new WSException(msg);
        }
    }

}
