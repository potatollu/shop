package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.PropertyValue;
import java.util.List;

public interface PropertyValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PropertyValue entity);

    PropertyValue selectByPrimaryKey(Long id);

    List<PropertyValue> selectAll();

    int updateByPrimaryKey(PropertyValue entity);

    List<PropertyValue> selectByPropertyId(Long propertyId);
}