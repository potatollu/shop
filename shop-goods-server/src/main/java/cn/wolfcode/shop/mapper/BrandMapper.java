package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Brand;
import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand entity);

    Brand selectByPrimaryKey(Long id);

    List<Brand> selectAll();

    int updateByPrimaryKey(Brand entity);
}