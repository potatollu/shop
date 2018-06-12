package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.OrderAction;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;

import java.util.List;

/**
 * Created by leo on 2018/2/24.
 */
public interface IOrderActionService {


    List<OrderAction> getOrderAction(Long orderId);

    /**
     * 记录操作日志
     *
     * @param orderInfo
     * @param vo
     */
    void recordOpsLog(OrderInfo orderInfo, OrderStatusChangeVO vo);
}
