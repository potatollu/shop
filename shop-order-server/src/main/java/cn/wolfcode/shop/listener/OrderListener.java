package cn.wolfcode.shop.listener;

import cn.wolfcode.shop.contrants.Contrants;
import cn.wolfcode.shop.service.IOrderService;
import cn.wolfcode.shop.vo.OrderVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @Autowired
    private IOrderService orderService;

    @JmsListener(destination = Contrants.GENERATE_ORDER_MESSAGE,containerFactory = "jmsListenerContainerQueue")
    public void createOrderListener(String conten){
        OrderVO vo = JSON.parseObject(conten, OrderVO.class);
        orderService.createOrder(vo);
    }
}
