package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Property;

import java.util.List;

public interface IPropertyService {
    List<Property> queryByCataId(Long catalogId);

    void saveOrUpdate(Property property);

    void delete(Long id);
}
