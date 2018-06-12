package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Car entity);

    Car selectByPrimaryKey(Long id);

    List<Car> selectAll();

    int updateByPrimaryKey(Car entity);

    Car selectByUserIdAndSkuId(@Param("userId") Long userId, @Param("skuId") Long skuId);
}