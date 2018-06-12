package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.ProductSku;

import java.util.List;

public interface IProductSkuService {

    /**
     * 根据商品id查询商品的sku
     * @param productId
     * @return
     */
    List<ProductSku> queryByProductId(Long productId);

    /**
     * 根据skuSn获取
     * @param skuSn
     * @return
     */
    ProductSku queryByProductSn(String skuSn);

    /**
     * 根据sku查找
     * @param skuId
     * @return
     */
    ProductSku queryBySkuId(Long skuId);
}
