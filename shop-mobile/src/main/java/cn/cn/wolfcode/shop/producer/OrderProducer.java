package cn.cn.wolfcode.shop.producer;

import cn.wolfcode.shop.vo.OrderVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * activemq 订单消息生产者
 */
@Component
public class OrderProducer {

    @Autowired
    private Destination generaterOrderQueue;
    @Autowired
    private JmsTemplate jmsTemplate;

    public void createOrder(OrderVO vo){
        jmsTemplate.convertAndSend(generaterOrderQueue, JSON.toJSONString(vo));
    }

}
