package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Invoice;
import cn.wolfcode.shop.domain.OrderInfo;

/**
 * Created by Administrator on 2018/4/13.
 */
public interface IInvoiceService {

    /**
     * 创建发票
     *
     * @param invoice
     */
    void create(Invoice invoice);

    void create(Invoice invoice, OrderInfo orderInfo);
}
