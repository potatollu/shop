package cn.wolfcode.shop.handler;


import cn.wolfcode.shop.exception.WSException;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class WSExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(WSExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HandlerMethod hm, Exception e,HttpServletResponse response) throws IOException {

        WSResponseVO vo = new WSResponseVO(WSResponseVO.DEFAULT_FALIED_CODE, WSResponseVO.DEFAULT_FALIED_MSG);

        logger.error("controller出错了",e);

        response.setContentType("application/json;charset=UTF-8");

        //看报出异常中是否有WSExcetion这个异常,没有就返回-1
        if (e.getMessage().indexOf(WSException.class.toString())>0){
            String errMsg = e.getMessage().substring(e.getMessage().indexOf(":")).trim();
            vo.setMsg(errMsg);
        }

        //如果出异常,对不同的返回类型做处理
        if(hm.getMethodAnnotation(ResponseBody.class) != null){
            //1.返回json,输出错误信息
            response.getWriter().print(JSON.toJSON(vo));
        }else {
            //2.非json,跳转到错误页面
            response.sendRedirect("/error/50x.html");
        }
    }

}
