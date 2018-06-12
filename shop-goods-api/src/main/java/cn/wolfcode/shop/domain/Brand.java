package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Brand extends BaseDomain{

    //品牌名称
    private String name;
    //创建时间
    private Date createTime;
    //排序
    private Integer sort;
    //商标
    private String logo;
    //描述
    private String description;
    //网址
    private String url;

}