package cn.cn.wolfcode.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
@PropertySource("classpath:activemq.properties")
public class ShopMobileLuancher {


    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopMobileLuancher.class,args);
    }

}
