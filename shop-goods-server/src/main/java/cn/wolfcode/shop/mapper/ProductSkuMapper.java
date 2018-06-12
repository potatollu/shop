package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.domain.SkuProperty;

import java.util.List;

public interface ProductSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSku entity);

    ProductSku selectByPrimaryKey(Long id);

    List<ProductSku> selectAll();

    int updateByPrimaryKey(ProductSku entity);

    List<ProductSku> queryByProductId(Long productId);

    ProductSku queryByProductSn(String skuSn);

}