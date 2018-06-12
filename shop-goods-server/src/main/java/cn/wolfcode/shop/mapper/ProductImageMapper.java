package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductImage;
import java.util.List;

public interface ProductImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductImage entity);

    ProductImage selectByPrimaryKey(Long id);

    List<ProductImage> selectAll();

    int updateByPrimaryKey(ProductImage entity);

    void batchInsert(List<ProductImage> imagesList);

}