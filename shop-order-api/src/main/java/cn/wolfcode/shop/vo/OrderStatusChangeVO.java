package cn.wolfcode.shop.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by leo on 2018/2/24.
 */
@Setter
@Getter
public class OrderStatusChangeVO implements Serializable {

    // 生成订单
    public static final int ORDER_CHANGE_TYPE_GENORDER        = 0;
    // 确认订单
    public static final int ORDER_CHANGE_TYPE_OK              = 1;
    // 已发货
    public static final int ORDER_CHANGE_TYPE_SHIPPED         = 2;
    // 订单完成
    public static final int ORDER_CHANGE_TYPE_COMPLETE        = 3;
    // 售后
    public static final int ORDER_CHANGE_TYPE_AFTER_SALES     = 4;
    // 确认收货
    public static final int ORDER_CHANGE_TYPE_CONFIRM_RECEIPT = 5;


    // 操作所关联的订单id
    private Long    orderId;
    // 操作类型:比如说确认订单,取消订单,确认收货(1为确认订单操作，2为发货，3为订单完成，4为售后, 5为确认收货)
    private Integer changeType;
    // 这一次操作的备注
    private String  note;
    // 这一次操作的用户名称
    private String  userName;
    // 用于区分是前台操作还是后台操作：0前台，1后台
    private Integer  place;
}
