package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.exception.WSException;
import cn.wolfcode.shop.mapper.UserInfoMapper;
import cn.wolfcode.shop.mapper.UserLoginMapper;
import cn.wolfcode.shop.redis.UserKeyPrefix;
import cn.wolfcode.shop.util.AssertUtil;
import cn.wolfcode.shop.util.PasswordUtil;
import cn.wolfcoe.shop.domain.UserInfo;
import cn.wolfcoe.shop.domain.UserLogin;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisServiceImpl redisService;

    @Transactional(rollbackFor = Exception.class)
    public void regist(UserLogin user, String phone) throws WSException{
        //检验用户不能重复
        UserLogin userLogin = userLoginMapper.selectByUsername(user.getUsername());
        if (userLogin != null){
            throw new WSException("用户已经存在");
        }
        //重置userLogin的状态为正常
        user.setStatus(UserLogin.USER_STATUS_NOMAL);
        user.setPassword(PasswordUtil.MD5(user.getPassword(),user.getUsername()));
        //保存userLogin
        userLoginMapper.insert(user);
        //构建userinfo,设置相关信息
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setPhone(phone);
        userInfo.setRegistTime(new Date());
        //保存userinfo
        userInfoMapper.insert(userInfo);
    }

    public String login(UserLogin userLogin) {
        UserLogin userInDB = userLoginMapper.selectByUsername(userLogin.getUsername());
        //判断是否存在这个用户
        AssertUtil.isNull(userInDB,"用户名或密码错误");
        //判断密码是否正确
        String pwd = PasswordUtil.MD5(userLogin.getPassword(), userLogin.getUsername());
        AssertUtil.faile(!pwd.equals(userInDB.getPassword()),"密码错误");
        //生产token
        String token = this.getToken();
        //保存到redis
        UserLogin userLogin1 = userLoginMapper.selectByPrimaryKey(userInDB.getId());
        redisService.set(UserKeyPrefix.USER_TOKEN_KEY_PREFIX, token,userLogin1);
        return token;
    }

    public void logout(String token) {
        redisService.del(UserKeyPrefix.USER_TOKEN_KEY_PREFIX,token);
    }

    public UserLogin getByToken(String token) {
        return redisService.get(UserKeyPrefix.USER_TOKEN_KEY_PREFIX,token);
    }

    public UserInfo getById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    public String getToken() {
        String token = UUID.randomUUID().toString().replace("-","").toUpperCase();
        return token;
    }
}
