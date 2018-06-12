package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static cn.wolfcode.shop.domain.OrderInfo.*;

@Getter
@Setter
public class OrderAction extends BaseDomain {
    private Long    orderId;
    private String  actionUser;
    private Integer orderStatus;
    private Integer flowStatus;
    private Integer payStatus;
    private Integer actionPlace;
    private String  actionNote;
    private Date    actionTime;


    /**
     * 订单状态：0为未确认，1为已确认，2,为已完成，3为取消
     *
     * @return
     */
    public String getOrderStatusStr() {
        return OrderInfo.getOrderStatusStr(this.orderStatus);
    }

    /**
     * 订单物流状态：0为未发货，1为已发货，2为确认收货，3为退货
     *
     * @return
     */
    public String getFlowStatusStr() {
        switch (this.flowStatus) {
            case FLOW_STATUS_UNSHIPPED:
                return "未发货";
            case FLOW_STATUS_SHIPPED:
                return "已发货";
            case FLOW_STATUS_COMPLETE:
                return "确认收货";
            case FLOW_STATUS_RETURNED:
                return "退货";
            default:
                return "未发货";
        }
    }

    /**
     * 订单支付状态：0为未付款，1为付款
     *
     * @return
     */
    public String getPayStatusStr() {
        switch (this.payStatus) {
            case PAY_STATUS_NO_PAY:
                return "未付款";
            case PAY_STATUS_PAYED:
                return "付款";
            default:
                return "未付款";
        }
    }
}