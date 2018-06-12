package cn.wolfcode.shop.service;

import cn.wolfcode.shop.domain.Car;

public interface IShoppingCarService {
    /**
     *新增购物车记录
     * @param skuSn
     * @param productNum
     * @param token
     */
    void add(String skuSn, Integer productNum, String token);

    /**
     * 根据页面传过来的carId 查找数据库
     * @param id
     * @return
     */
    Car getById(Long id);

    /**
     * 生成订单后删除购物车记录
     * @param id
     */
    void delectByCarId(Long id);
}
