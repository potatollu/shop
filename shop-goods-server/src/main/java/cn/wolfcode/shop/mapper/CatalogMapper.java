package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.vo.CatalogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatalogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Catalog entity);

    Catalog selectByPrimaryKey(Long id);

    List<Catalog> selectAll();

    int updateByPrimaryKey(Catalog entity);

    List<Catalog> selectByParentId(@Param("id") Long id);

    Long getParentChildrenById(Long id);

    Integer selectProductCountByCatalogId(Long id);

    Integer selectPropertyCountByCatalogId(Long id);

    List<Catalog> queryParentByCatalogId(Long catalogId);
}