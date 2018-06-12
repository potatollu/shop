package cn.cn.wolfcode.shop.controller;

import cn.wolfcode.shop.controller.BaseController;
import cn.wolfcode.shop.vo.WSResponseVO;
import cn.wolfcoe.shop.domain.UserLogin;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/users")
@Validated
public class UserController extends BaseController {

    @Reference
    private IUserService userService;

    /**
     * 注册
     * @param user
     * @param phone
     * @return
     */
    @PostMapping("/regist")
    public WSResponseVO regist(UserLogin user, String phone){
        userService.regist(user,phone);
        return success();
    }

    /**\
     * 登录
     * @param userLogin
     * @return
     */
    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public WSResponseVO login(@Valid UserLogin userLogin){
        String token = userService.login(userLogin);
        return success(token);
    }
    
    @RequestMapping(value = "/logout",method=RequestMethod.DELETE)
    @ResponseBody
    public WSResponseVO logout(@RequestHeader(value = "token",required = false)@NotNull(message = "user.token.notnull") String token){
        userService.logout(token);
        return success();
    }
    
}
