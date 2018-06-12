package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Car extends BaseDomain{

    private Long userId;

    private Long skuId;

    private String productName;

    private Integer productNumber;

    private String productImg;

}