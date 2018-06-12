package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.contrants.Contrants;
import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.InvoiceMapper;
import cn.wolfcode.shop.mapper.OrderInfoMapper;
import cn.wolfcode.shop.mapper.OrderProductMapper;
import cn.wolfcode.shop.mapper.OrderProductPropertyMapper;
import cn.wolfcode.shop.qo.OrderQueryObject;
import cn.wolfcode.shop.qo.PageResult;
import cn.wolfcode.shop.service.*;
import cn.wolfcode.shop.util.AssertUtil;
import cn.wolfcode.shop.util.IdGenerateUtil;
import cn.wolfcode.shop.vo.OrderStatusChangeVO;
import cn.wolfcode.shop.vo.OrderVO;
import cn.wolfcoe.shop.domain.UserAddress;
import cn.wolfcoe.shop.domain.UserLogin;
import cn.wolfcoe.shop.service.IUserAddressService;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements IOrderService{

    @Reference
    private IUserService userService;
    @Reference
    private IUserAddressService userAddressService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Reference
    private IShoppingCarService shoppingCarService;
    @Reference
    private IProductSkuService productSkuService;
    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private OrderProductPropertyMapper orderProductPropertyMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private IOrderActionService actionService;
    /**
     * 生成订单
     * @param
     * vo
     */
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderVO vo) {
        OrderInfo orderInfo = new OrderInfo();
        UserLogin currentUser = userService.getByToken(vo.getToken());
        //1.生成订单编号
        long orderSn = IdGenerateUtil.get().nextId();
        //2.根据用户收获地址id查询对应的收获地址对象
        UserAddress userAddress = userAddressService.getById(vo.getUserAddressId());
        AssertUtil.faile(!userAddress.getId().equals(currentUser.getId()),"非法操作");
        //3.设置用户收获地址相关信息
        BeanUtils.copyProperties(userAddress,orderInfo);
        //4.重置订单状态
        orderInfo.setFlowStatus(OrderInfo.FLOW_STATUS_UNSHIPPED);
        orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_NO_OK);
        orderInfo.setPayStatus(OrderInfo.PAY_STATUS_NO_PAY);
        //5.设置订单基础信息
        orderInfo.setUserId(currentUser.getId());
        orderInfo.setPayType(vo.getPay().getPayType());
        orderInfo.setOrderSn(orderSn+"");
        orderInfo.setOrderTime(new Date());
        orderInfo.setOrderAmount(BigDecimal.ZERO);
        //先保存订单获取订单id
        orderInfoMapper.insert(orderInfo);
        //6.根据购物车id,查询购物车列表
        BigDecimal orderAmount = BigDecimal.ZERO;
        for (Car car : vo.getCarList()) {
            //7.根据购物车列表生产订单
            Car carInDB = shoppingCarService.getById(car.getId());
            OrderProduct orderProduct = new OrderProduct();
            BeanUtils.copyProperties(carInDB,orderProduct);
            orderProduct.setOrderId(orderInfo.getId());
            //查询商品相关的sku信息
            ProductSku sku = productSkuService.queryBySkuId(carInDB.getSkuId());
            orderProduct.setProductPrice(sku.getPrice());
            //计算明细小计
            BigDecimal totalAmount = new BigDecimal(orderProduct.getProductNumber()).multiply(
                    orderProduct.getProductPrice()).setScale(2, RoundingMode.HALF_UP);
            orderProduct.setTotalPrice(totalAmount);
            //保存订单明细
            orderProductMapper.insert(orderProduct);
            //8.构建订单商品所关联的sku和sku属性
            for (ProductSkuProperty property : sku.getProductSkuPropertyList()) {
                OrderProductProperty orderProductProperty = new OrderProductProperty();
                orderProductProperty.setName(property.getSkuProperty().getName());
                orderProductProperty.setProductId(sku.getProductId());
                orderProductProperty.setValue(property.getValue());
                //保存
                orderProductPropertyMapper.insert(orderProductProperty);
            }
            //计算订单金额
            orderAmount = orderAmount.add(totalAmount);
        }
        orderInfo.setOrderAmount(orderAmount);
        //跟新订单信息
        orderInfoMapper.updateByPrimaryKey(orderInfo);
        //9.保存发票
        Invoice invoice = vo.getInvoice();
        invoice.setOrderId(orderInfo.getId());
        invoice.setUserId(currentUser.getId());
        invoiceMapper.insert(invoice);

        //删除已生成订单的购物车记录
        vo.getCarList().forEach(car -> {
            shoppingCarService.delectByCarId(car.getId());
        });

        //记录订单日志
        OrderStatusChangeVO changeVO = new OrderStatusChangeVO();
        changeVO.setUserName(currentUser.getUsername());
        changeVO.setPlace(Contrants.USER_PLACE_MGR);
        changeVO.setNote("创建订单");
        changeVO.setOrderId(orderInfo.getId());
        changeVO.setChangeType(OrderStatusChangeVO.ORDER_CHANGE_TYPE_GENORDER);
        actionService.recordOpsLog(orderInfo,changeVO);

    }
    public PageResult query(OrderQueryObject qo) {
        int count = orderInfoMapper.queryCount(qo);
        if (count == 0) {
            return PageResult.empty();
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.queryList(qo);

        return new PageResult(orderInfoList, count, qo.getCurrentPage(), qo.getPageSize());
    }

    public OrderInfo getById(Long id) {
        return orderInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改订单状态
     * @param vo
     */
    public void changeStatus(OrderStatusChangeVO vo) {
        //1.从数据库查询该订单对象
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(vo.getOrderId());
        Integer orderStatus = orderInfo.getOrderStatus();
        Integer flowStatus = orderInfo.getFlowStatus();
        Integer payStatus = orderInfo.getPayStatus();
        //2.判断订单是否可修改
        switch (vo.getChangeType()){
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_OK:
                //确认订单状态
                AssertUtil.faile(!(orderStatus == OrderInfo.ORDER_STATUS_NO_OK),"请勿重复确认订单");
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_SHIPPED:
                //已发货
                AssertUtil.faile(!(orderStatus == OrderInfo.ORDER_STATUS_OK),"请先确认订单");
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_COMPLETE:
                //订单完成
                AssertUtil.faile(!(orderStatus == OrderInfo.ORDER_STATUS_OK),"请先确认订单");
                AssertUtil.faile(!(orderStatus == OrderInfo.FLOW_STATUS_SHIPPED),"请先发货再确认订单");
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_AFTER_SALES:
                //售后
                AssertUtil.faile(!(flowStatus == OrderInfo.FLOW_STATUS_SHIPPED),"请先发货再进行售后");
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_CONFIRM_RECEIPT:
                //确认收货
                AssertUtil.faile(!(orderStatus == OrderInfo.ORDER_STATUS_OK),"请先确认订单");
                AssertUtil.faile(!(flowStatus == OrderInfo.FLOW_STATUS_SHIPPED),"请先发货");
                AssertUtil.faile(!(payStatus == OrderInfo.PAY_STATUS_PAYED),"请先付款后确认订单");
                break;
        }
        //3.修改订单状态
        switch (vo.getChangeType()){
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_OK:
                //如果当前是确认定案,将订单状态设置为ok
                orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_OK);
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_SHIPPED:
                //将当前物流状态设置为已发货
                orderInfo.setFlowStatus(OrderInfo.FLOW_STATUS_SHIPPED);
                orderInfo.setPayStatus(OrderInfo.PAY_STATUS_PAYED);
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_COMPLETE:
                //将当前的订单状态设置为已完成
                orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_COMPLETE);
                break;
            case OrderStatusChangeVO.ORDER_CHANGE_TYPE_CONFIRM_RECEIPT:
                //确认订单
                orderInfo.setOrderStatus(OrderInfo.ORDER_STATUS_COMPLETE);
                orderInfo.setFlowStatus(OrderInfo.FLOW_STATUS_COMPLETE);
                break;
        }
        //修改订单状态
        orderInfoMapper.updateByPrimaryKey(orderInfo);
        //记录订单状态跟新操作
        actionService.recordOpsLog(orderInfo,vo);
    }

    public void changeStatus(OrderStatusChangeVO vo, String token) {
        //1.根据token获取当前用户
        UserLogin userLogin = userService.getByToken(token);
        //2.获取订单对象
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(vo.getOrderId());
        //3.校验是否是当前用户的操作
        AssertUtil.faile(!(userLogin.getId().equals(orderInfo.getUserId())),"非法操作");
        //4.设置前端用户相关信息
        vo.setPlace(Contrants.USER_PLACE_APP);
        vo.setUserName(userLogin.getUsername());
        //5.更新订单状态
        changeStatus(vo);
    }
}
