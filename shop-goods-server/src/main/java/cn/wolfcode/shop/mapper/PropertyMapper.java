package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Property;
import java.util.List;

public interface PropertyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Property entity);

    Property selectByPrimaryKey(Long id);

    List<Property> selectAll();

    int updateByPrimaryKey(Property entity);

    List<Property> queryByCataId(Long catalogId);
}