package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.PropertyValue;

import java.util.List;

public interface IPropertyValueService {
    List<PropertyValue> selectByPropertyId(Long propertyId);

    void savePropertiesValue(List<PropertyValue> propertyValues);

    void delete(Long id);
}
