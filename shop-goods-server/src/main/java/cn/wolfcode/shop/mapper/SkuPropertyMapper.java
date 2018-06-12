package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.SkuProperty;
import java.util.List;

public interface SkuPropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuProperty entity);

    SkuProperty selectByPrimaryKey(Long id);

    List<SkuProperty> selectAll();

    int updateByPrimaryKey(SkuProperty entity);

    List<SkuProperty> queryByCataId(Long catalogId);

    List<SkuProperty> queryPropertiesByCatalogId(Long id);

    List<SkuProperty> selectByProductId(Long productId);
}