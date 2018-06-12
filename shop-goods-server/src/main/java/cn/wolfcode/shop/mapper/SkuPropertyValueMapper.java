package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.SkuPropertyValue;
import java.util.List;

public interface SkuPropertyValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuPropertyValue entity);

    SkuPropertyValue selectByPrimaryKey(Long id);

    List<SkuPropertyValue> selectAll();

    int updateByPrimaryKey(SkuPropertyValue entity);

    List<SkuPropertyValue> selectByPropertyId(Long propertyId);
}