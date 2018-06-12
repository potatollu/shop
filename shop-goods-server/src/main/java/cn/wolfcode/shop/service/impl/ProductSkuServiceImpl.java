package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.mapper.ProductSkuMapper;
import cn.wolfcode.shop.service.IProductSkuService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductSkuServiceImpl implements IProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    public List<ProductSku> queryByProductId(Long productId) {
        return productSkuMapper.queryByProductId(productId);
    }

    public ProductSku queryByProductSn(String skuSn) {
        return productSkuMapper.queryByProductSn(skuSn);
    }

    public ProductSku queryBySkuId(Long skuId) {
        return productSkuMapper.selectByPrimaryKey(skuId);
    }
}
