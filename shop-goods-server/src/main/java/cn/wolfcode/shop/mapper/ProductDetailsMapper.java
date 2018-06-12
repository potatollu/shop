package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.ProductDetails;
import java.util.List;

public interface ProductDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductDetails entity);


    List<ProductDetails> selectAll();

    int updateByPrimaryKey(ProductDetails entity);
}