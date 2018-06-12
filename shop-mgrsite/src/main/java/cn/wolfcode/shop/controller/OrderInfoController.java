package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.contrants.Contrants;
import cn.wolfcode.shop.domain.OrderAction;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.service.IOrderActionService;
import cn.wolfcode.shop.service.IOrderService;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;
import cn.wolfcode.shop.vo.WSResponseVO;
import cn.wolfcoe.shop.domain.UserInfo;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2018/2/23.
 */
@Controller
@RequestMapping(value = "/order")
public class OrderInfoController extends BaseController {

    @Reference
    private IOrderService orderInfoService;
    @Reference
    private IUserService userInfoService;
    @Reference
    private IOrderActionService orderActionService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String orderInfo(){

        return "order/order_info";
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public String page(Map map, OrderQueryObject qo){
        PageResult page = orderInfoService.query(qo);
        map.put("page",page);
        return "order/order_list";
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(Long id,Map map){
        OrderInfo orderInfo = orderInfoService.getById(id);
        UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
        List<OrderAction> orderActionList =  orderActionService.getOrderAction(id);
        map.put("userInfo",userInfo);
        map.put("orderInfo",orderInfo);
        map.put("orderActionList",orderActionList);
        return "order/order_detail";
    }

    @PatchMapping("/changeStatus")
    @ResponseBody
    public WSResponseVO changeStatus(OrderStatusChangeVO vo){
        vo.setUserName("admin");
        vo.setPlace(Contrants.USER_PLACE_MGR);
        orderInfoService.changeStatus(vo);
        return success();
    }

}
