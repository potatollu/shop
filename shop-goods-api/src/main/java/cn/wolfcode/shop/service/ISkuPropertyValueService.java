package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.domain.SkuPropertyValue;

import java.util.List;

public interface ISkuPropertyValueService {
    List<SkuPropertyValue> selectByPropertyId(Long propertyId);

    void savePropertiesValue(List<SkuPropertyValue> propertyValues);

    void delete(Long id);
}
