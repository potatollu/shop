package cn.cn.wolfcode.shop.config;

import cn.wolfcode.shop.contrants.Contrants;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;

@Configuration
public class ActiveMQConfig {


    @Bean
    public Destination generateOrderQueue(){
        return new ActiveMQQueue(Contrants.GENERATE_ORDER_MESSAGE);
    }
}
