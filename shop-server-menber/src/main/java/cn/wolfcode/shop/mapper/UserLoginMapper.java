package cn.wolfcode.shop.mapper;

import cn.wolfcoe.shop.domain.UserLogin;
import java.util.List;

public interface UserLoginMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLogin entity);

    UserLogin selectByPrimaryKey(Long id);

    List<UserLogin> selectAll();

    int updateByPrimaryKey(UserLogin entity);

    UserLogin selectByUsername(String username);
}