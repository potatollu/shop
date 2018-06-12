package cn.wolfcode.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PayVO implements Serializable{
    /**
     * 余额付款
     */
    public static final int PAY_FOR_BALANCE = 0;
    /**
     * 在线付款
     */
    public static final int PAY_FOR_ONLINE  = 1;
    /**
     * 货到付款
     */
    public static final int PAY_FOR_OFFLINE = 2;

    // 支付类型:0为余额付款，1为在线付款，2为货到付款
    private Integer payType;
}
