package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.qo.QueryObject;
import cn.wolfcode.shop.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * Created by leo on 2018/1/10.
 */
public interface IProductService {

    List<Product> getAllProduct();

    PageResult productPage(QueryObject qo);

    /**
     * 保存商品
     */
    void save(ProductVO ProductVO);

    /**
     * 通过商品id获取商品对象
     *
     * @param productId
     * @return
     */
    Product getProductById(Long productId);


    /**
     * 生成sku实现
     *
     * @param vo
     * @return
     */
    /*List<Map<String, Object>> generateSku(GenerateVO vo);*/
}
