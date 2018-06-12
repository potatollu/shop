package cn.wolfcoe.shop.service;


import cn.wolfcode.shop.exception.WSException;
import cn.wolfcoe.shop.domain.UserInfo;
import cn.wolfcoe.shop.domain.UserLogin;

public interface IUserService {
    /**
     * 注册
     * @param user
     * @param phone
     */
    void regist(UserLogin user, String phone);

    /**
     * 登录
     * @param userLogin
     * @return
     */
    String login(UserLogin userLogin)  throws WSException;

    /**
     * 注销
     * @param token
     */
    void logout(String token);

    /**
     * 获取当前登录用户
     * @param token
     * @return
     */
    UserLogin getByToken(String token);

    UserInfo getById(Long id);
}
