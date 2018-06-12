package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class Product extends BaseDomain{

    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品编号
     */
    private String sn;
    /**
     * 商品分类id
     */
    private Catalog catalog;
    /**
     * 商品品牌id
     */
    private Brand brand;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 基本价
     */
    private BigDecimal basePrice;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
    /**
     * 商品封面
     */
    private String image;
    /**
     * 商品关键字：如果有多个使用,分割
     */
    private String keyword;
    /**
     * 商品标签：如果有多个使用,分割
     */
    private String label;


}