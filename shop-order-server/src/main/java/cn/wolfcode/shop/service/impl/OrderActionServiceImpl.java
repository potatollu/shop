package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.OrderAction;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.mapper.OrderActionMapper;
import cn.wolfcode.shop.service.IOrderActionService;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by leo on 2018/2/24.
 */
@Service
public class OrderActionServiceImpl implements IOrderActionService {

    @Autowired
    private OrderActionMapper orderActionMapper;

    public List<OrderAction> getOrderAction(Long orderId) {
        return orderActionMapper.getOrderAction(orderId);
    }

    public void recordOpsLog(OrderInfo orderInfo, OrderStatusChangeVO vo) {
        OrderAction orderAction = new OrderAction();
        orderAction.setPayStatus(orderInfo.getPayStatus());
        orderAction.setFlowStatus(orderInfo.getFlowStatus());
        orderAction.setOrderId(orderInfo.getId());
        orderAction.setOrderStatus(orderInfo.getOrderStatus());
        orderAction.setActionNote(vo.getNote());
        orderAction.setActionPlace(vo.getPlace());
        orderAction.setActionTime(new Date());
        orderAction.setActionUser(vo.getUserName());

        // 保存操作日志
        orderActionMapper.insert(orderAction);
    }
}
