package cn.wolfcode.shop.mapper;

import cn.wolfcoe.shop.domain.UserAddress;
import java.util.List;

public interface UserAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAddress entity);

    UserAddress selectByPrimaryKey(Long id);

    List<UserAddress> selectAll();

    int updateByPrimaryKey(UserAddress entity);
}