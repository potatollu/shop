package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.ProductDetailsMapper;
import cn.wolfcode.shop.mapper.ProductImageMapper;
import cn.wolfcode.shop.mapper.ProductMapper;
import cn.wolfcode.shop.mapper.ProductPropertyValueMapper;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.qo.QueryObject;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.vo.ProductVO;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by leo on 2018/1/10.
 */
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private ProductPropertyValueMapper productPropertyValueMapper;
    @Autowired
    private ICatalogService catalogService;


    public List<Product> getAllProduct() {
        return productMapper.selectAll();
    }

    public PageResult productPage(QueryObject qo) {
        int totalCount = productMapper.queryCount(qo);
        List data = productMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ProductVO productVO) {
        //保存商品基本信息
        Product product = productVO.getProduct();
        Date date = new Date();
        product.setCreateTime(date);
        product.setLastModifiedDate(date);
        productMapper.insert(product);
        //保存商品详情
        ProductDetails details = productVO.getProductDetails();
        details.setProductId(product.getId());
        productDetailsMapper.insert(details);
        //保存商图片
        Iterator<ProductImage> iterator = productVO.getProductImagesList().iterator();
        while (iterator.hasNext()){
            ProductImage next = iterator.next();
            if (next.getImagePath() == null || "".equals(next.getImagePath().trim())){
                iterator.remove();
            }
            next.setProductId(product.getId());
        }
        List<ProductImage> imagesList = productVO.getProductImagesList();
        //批量保存
        productImageMapper.batchInsert(imagesList);

        //保存商品属性
        List<ProductPropertyValue> valueList = productVO.getProductPropertyValueList();
        valueList.forEach(value->{
            value.setProductId(product.getId());
        });
        productPropertyValueMapper.batchInsert(valueList);

    }

    public Product getProductById(Long productId) {
        return productMapper.selectByPrimaryKey(productId);
    }
}
