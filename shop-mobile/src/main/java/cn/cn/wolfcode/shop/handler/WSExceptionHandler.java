package cn.cn.wolfcode.shop.handler;


import cn.wolfcode.shop.exception.WSException;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class WSExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(WSExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HandlerMethod hm,Exception e, HttpServletResponse response) throws Exception {

        WSResponseVO vo = new WSResponseVO(WSResponseVO.DEFAULT_FALIED_CODE, WSResponseVO.DEFAULT_FALIED_MSG);

        logger.error("controller出错了",e);

        response.setContentType("application/json;charset=UTF-8");
        StringBuilder sb = new StringBuilder(100);

        //看报出异常中是否有WSExcetion这个异常,没有就返回-1
        if (e instanceof WSException){
           vo.setMsg(e.getMessage());
        }else if(e instanceof BindException){
            List<ObjectError> allErrors = ((BindException) e).getBindingResult().getAllErrors();
            for (ObjectError allError : allErrors) {
                sb.append(allError.getDefaultMessage() + "\r\n");
            }
            vo.setCode(WSResponseVO.INVALID_PARAM_CODE);
            vo.setMsg(sb.toString());
        }else if(e instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException) e;
            for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                sb.append(violation.getMessage()).append(";");
            }
            vo.setCode(WSResponseVO.INVALID_PARAM_CODE);
            vo.setMsg(sb.deleteCharAt(sb.length()-1).toString());
        }

        /*//如果出异常,对不同的返回类型做处理
        if(hm.getMethodAnnotation(ResponseBody.class) != null){
            //1.返回json,输出错误信息
            response.getWriter().print(JSON.toJSON(vo));
        }else {
            //2.非json,跳转到错误页面
            response.sendRedirect("/error/50x.html");
        }*/
        //返回json
        response.getWriter().print(JSON.toJSON(vo));
    }

}
