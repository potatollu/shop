package cn.wolfcode.shop;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.service.ICatalogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时器对象
 */
@Component
public class CatalogScheduler {

    @Reference
    private ICatalogService catalogService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CatalogScheduler.class);

    /**
     * 需求:每年凌晨三点计算所有分类的商品数量和属性数量
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void countNumber(){
        //获取数据库中的所有分类
        List<Catalog> catalogs = catalogService.listAll();
        catalogs.forEach(catalog -> {
            //计算它们的商品数量和属性数量
            Integer productCount = catalogService.selectProductCountByCatalogId(catalog.getId());
            Integer propertyCount = catalogService.selectPropertyCountByCatalogId(catalog.getId());
            //放入redis缓存中
            HashOperations<String, String, Integer> hash = redisTemplate.opsForHash();
            Map<String, Integer> map = new HashMap<>();
            map.put("productCount",productCount);
            map.put("propertyCount",propertyCount);
            hash.putAll("catalog:"+catalog.getId(),map);
            //System.out.println("定时任务结束,数量统计完成"+productCount+propertyCount);
        });
    }


}
