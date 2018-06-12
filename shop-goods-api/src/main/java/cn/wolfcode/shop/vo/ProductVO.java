package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 2018/2/14.
 */
@Setter
@Getter
public class ProductVO implements Serializable {

    private Product product;
    private ProductDetails productDetails;
    private List<ProductImage> productImagesList;
    private List<ProductPropertyValue> productPropertyValueList;
}
