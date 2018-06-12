package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductSkuVo;

import java.util.List;
import java.util.Map;

public interface ISkuPropertyService {
    List<SkuProperty> queryByCataId(Long catalogId);

    void saveOrUpdate(SkuProperty skuProperty);

    void delete(Long id);

    List<SkuProperty> queryPropertiesByCatalogId(Long id);

    SkuProperty getById(Long skuPropertyId);

    /**
     * 生成sku
     * @param vo
     */
    List<Map<String,Object>> generateSku(GenerateSkuVo vo);

    /**
     * 保存生成出来的sku
     * @param vo
     */
    void save(ProductSkuVo vo);

    List<SkuProperty> queryByProductId(Long productId);
}
