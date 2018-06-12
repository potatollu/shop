package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.Car;
import cn.wolfcode.shop.domain.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付对象
 */
@Setter
@Getter
public class OrderVO implements Serializable{
    private Long userAddressId;
    private PayVO pay;
    private List<Car> carList = new ArrayList<Car>();
    private Invoice invoice;
    private String token;
}
