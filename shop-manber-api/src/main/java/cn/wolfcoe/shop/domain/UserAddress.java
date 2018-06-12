package cn.wolfcoe.shop.domain;

import cn.wolfcode.shop.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAddress extends BaseDomain{

    //用户id
    private Integer userId;
    //收货人
    private String consignee;
    //收货人
    private String phone;
    //收货人
    private String country;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //详细地址
    private String address;
    //邮编
    private String zipcode;
    //默认地址，0否，1是
    private Byte status;

}