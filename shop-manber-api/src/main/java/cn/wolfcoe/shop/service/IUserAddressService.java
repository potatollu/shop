package cn.wolfcoe.shop.service;

import cn.wolfcoe.shop.domain.UserAddress;

public interface IUserAddressService {

    /**
     * 根据
     * @param userAddressId
     * @return
     */
    UserAddress getById(Long userAddressId);
}
