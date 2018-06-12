package cn.cn.wolfcode.shop.controller;

import cn.cn.wolfcode.shop.producer.OrderProducer;
import cn.wolfcode.shop.contrants.Contrants;
import cn.wolfcode.shop.controller.BaseController;
import cn.wolfcode.shop.service.IOrderService;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;
import cn.wolfcode.shop.vo.OrderVO;
import cn.wolfcode.shop.vo.WSResponseVO;
import cn.wolfcoe.shop.domain.UserLogin;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.Configuration;

@Controller
@RequestMapping("/api/orders")
public class OrderController extends BaseController{

    @Autowired
    private OrderProducer orderProducer;
    @Reference
    private IUserService userService;
    @Reference
    private IOrderService orderService;

    @PostMapping("/createOrder")
    public WSResponseVO createrOrder(@RequestBody OrderVO vo, @RequestHeader("token")String token){
        vo.setToken(token);
        //orderService.createOrder(vo);
        orderProducer.createOrder(vo);
        return success();
    }

    @PatchMapping("/ok")
    public WSResponseVO ok(@RequestBody OrderStatusChangeVO vo,@RequestHeader("token")String token){
        orderService.changeStatus(vo,token);
        return success();
    }

}
