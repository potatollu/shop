package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;
import cn.wolfcode.shop.vo.OrderVO;

public interface IOrderService {
    /**
     * 创建订单
     * @param vo
     */
    void createOrder(OrderVO vo);

    PageResult query(OrderQueryObject qo);

    OrderInfo getById(Long id);

    /**
     * 修改订单状态
     * @param vo
     */
    void changeStatus(OrderStatusChangeVO vo);

    /**
     * 前端用户更新状态
     * @param vo
     * @param token
     */
    void changeStatus(OrderStatusChangeVO vo, String token);
}
