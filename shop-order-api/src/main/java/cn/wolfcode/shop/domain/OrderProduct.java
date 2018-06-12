package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderProduct extends BaseDomain{
    //订单id
    private Long orderId;
    //商品skuid
    private Long skuId;
    //商品名
    private String productName;
    //购买数量
    private Integer productNumber;
    //商品价格
    private BigDecimal productPrice;
    //小计
    private BigDecimal totalPrice;
    //商品图片
    private String productImg;

    private List<OrderProductProperty> orderProductPropertyList = new ArrayList<OrderProductProperty>();

}