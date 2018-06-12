package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Invoice extends BaseDomain{
    //发票类型，1为普通发票，2为电子普通发票，3为增值税专用发票
    private Byte invoiceType;
    //发票抬头：1为个人，2为单位
    private Byte invoiceHead;
    //用户id
    private Long userId;
    //订单id
    private Long orderId;
    //发票内容：1为商品明细，2为商品类别
    private Integer invoiceContent;
    //收票人手机
    private String invoicePhone;
    //收票人邮箱
    private String invoiceEmail;

}