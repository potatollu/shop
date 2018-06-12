package cn.wolfcoe.shop.domain;

import cn.wolfcode.shop.domain.BaseDomain;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserLogin extends BaseDomain{

    //用户状态:正常
    public static final int USER_STATUS_NOMAL = 0;
    //用户状态:禁用
    public static final int USER_STATUS_DISABLE = 1;

    @NotNull(message = "用户名不能为空")
    private String username;

    @JSONField(serialize = false)
    @Length(min = 4,max = 20,message = "密码长度应该在4到20")
    private String password;

    private Integer status = USER_STATUS_NOMAL;
}