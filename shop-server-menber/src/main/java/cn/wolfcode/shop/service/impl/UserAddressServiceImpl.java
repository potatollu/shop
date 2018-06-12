package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.mapper.UserAddressMapper;
import cn.wolfcoe.shop.domain.UserAddress;
import cn.wolfcoe.shop.service.IUserAddressService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    public UserAddress getById(Long userAddressId) {
        return userAddressMapper.selectByPrimaryKey(userAddressId);
    }
}
