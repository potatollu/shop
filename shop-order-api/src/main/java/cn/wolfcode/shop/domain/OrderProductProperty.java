package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderProductProperty extends BaseDomain{

    //商品id
    private Long productId;
    //sku属性名称
    private String name;
    //sku属性值
    private String value;
}