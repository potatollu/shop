package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Invoice;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.mapper.InvoiceMapper;
import cn.wolfcode.shop.service.IInvoiceService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/4/13.
 */
@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public void create(Invoice invoice) {
        invoiceMapper.insert(invoice);
    }

    @Override
    public void create(Invoice invoice, OrderInfo orderInfo) {
        invoice.setOrderId(orderInfo.getId());
        invoice.setUserId(orderInfo.getUserId());
        invoiceMapper.insert(invoice);
    }
}
