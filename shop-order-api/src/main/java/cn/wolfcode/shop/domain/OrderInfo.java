package cn.wolfcode.shop.domain;

import cn.wolfcode.shop.vo.PayVO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrderInfo extends BaseDomain {

    public static final int ORDER_STATUS_NO_OK = 0;
    public static final int ORDER_STATUS_OK = 1;
    public static final int ORDER_STATUS_COMPLETE = 2;
    public static final int ORDER_STATUS_CANEL = 3;

    public static final int FLOW_STATUS_UNSHIPPED = 0;
    public static final int FLOW_STATUS_SHIPPED = 1;
    public static final int FLOW_STATUS_COMPLETE = 2;
    public static final int FLOW_STATUS_RETURNED = 3;

    public static final int PAY_STATUS_NO_PAY = 0;
    public static final int PAY_STATUS_PAYED = 1;


    //订单编号
    private String orderSn;
    //用户id
    private Long userId;
    //订单金额
    private BigDecimal orderAmount;
    //支付状态:付款方式,0为余额付款，1为在线付款，2为货到付款
    private Integer payType;
    //订单状态：0为未确认，1为已确认，2,为已完成，3为取消
    private Integer orderStatus;
    //订单物流状态：0为未发货，1为已发货，2为确认收货，3为已退货
    private Integer flowStatus;
    //订单支付状态：0为未付款，1为付款
    private Integer payStatus;
    //下单时间
    private Date orderTime;
    //手机号码
    private String phone;
    //联系人名称
    private String consignee;
    //国家
    private String country;
    //省
    private String province;
    //城市
    private String city;
    //区
    private String district;
    //详细地址
    private String address;
    //邮政编号
    private String zipcode;

    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

    //付款方式,0为余额付款，1为在线付款，2为货到付款
    public String getPayTypeStr() {
        switch (this.payType) {
            case PayVO.PAY_FOR_BALANCE:
                return "余额付款";
            case PayVO.PAY_FOR_ONLINE:
                return "在线付款";
            case PayVO.PAY_FOR_OFFLINE:
                return "货到付款";
            default:
                return "余额付款";
        }
    }

    public static void main(String[] args) {
        Integer payType = 0;
        System.out.println(PayTypeEnum.BALANCE.ordinal());
        System.out.println(PayTypeEnum.PAYONLINE.ordinal());
    }

    enum PayTypeEnum {
        BALANCE(0, "余额付款"), PAYONLINE(1, "在线付款");

        Integer payType;
        String payName;

        PayTypeEnum(Integer payType, String payName) {
            this.payName = payName;
        }

        public String getPayName() {
            return payName;
        }
    }

    /**
     * 订单状态：0为未确认，1为已确认，2,为已完成，3为取消
     * 订单物流状态：0为未发货，1为已发货，2为确认收货，3为退货
     * 订单支付状态：0为未付款，1为付款
     *
     * @return
     */
    public String getStatus() {
        //先判断是支付方式是什么
        if (payType == PayVO.PAY_FOR_OFFLINE) {
            return judge();
        } else {
            //判断付款状态是什么
            if (payStatus == PAY_STATUS_PAYED) {
                return judge();
            } else {
                return "未付款";
            }
        }

    }

    /**
     * 判断订单状态方法
     *
     * @return
     */
    private String judge() {
        //判断订单状态
        if (orderStatus == ORDER_STATUS_NO_OK) {
            return "未确认";
        } else if (orderStatus == ORDER_STATUS_OK) {
            if (flowStatus == FLOW_STATUS_SHIPPED) {
                return "已发货";
            } else {
                return "已确认";
            }
        } else if (orderStatus == ORDER_STATUS_COMPLETE) {
            if (flowStatus == FLOW_STATUS_RETURNED) {
                return "已退货";
            } else {
                return "已完成";
            }
        } else {
            return "取消";
        }
    }

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

    public static String getOrderStatusStr(Integer orderStatus) {
        switch (orderStatus) {
            case ORDER_STATUS_NO_OK:
                return "未确认";
            case ORDER_STATUS_OK:
                return "已确认";
            case ORDER_STATUS_COMPLETE:
                return "以完成";
            case ORDER_STATUS_CANEL:
                return "取消";
            default:
                return "未确认";
        }
    }

}