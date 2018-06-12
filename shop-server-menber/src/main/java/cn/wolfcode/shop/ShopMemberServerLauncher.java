package cn.wolfcode.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.wolfcode.shop.mapper")
@PropertySource("classpath:redis.properties")
public class ShopMemberServerLauncher extends AbstractBaseLauncher{
    public static void main(String[] args) {
        new ShopMemberServerLauncher().start(args);
    }
}
