package cn.wolfcode.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@MapperScan("cn.wolfcode.shop.mapper")
@EnableTransactionManagement
public class ShopGoodsServerLauncher {

    //
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();
    //日志
    private static final Logger logger = LoggerFactory.getLogger(ShopGoodsServerLauncher.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ShopGoodsServerLauncher.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            //停止线程
            context.stop();
            try {
                LOCK.lock();//上锁
                STOP.signal();//唤醒线程
            }finally {
                LOCK.unlock();//解锁
            }
        }));

        try {
            LOCK.lock();
            STOP.await();
        }catch (Exception e){
            logger.error("container start failed",e);
        }finally {
            LOCK.unlock();
        }

    }

}
