package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.vo.CatalogVo;

import java.util.List;

public interface ICatalogService {

    List<Catalog> listAll();

    List<CatalogVo> selectByParentId(Long id);

    void saveOrUpdate(Catalog catalog);

    void deleteById(Long id);

    void updateSort(Long[] ids);

    Integer selectProductCountByCatalogId(Long id);

    Integer selectPropertyCountByCatalogId(Long id);

    List<Catalog> queryParentByCatalogId(Long id);
}
