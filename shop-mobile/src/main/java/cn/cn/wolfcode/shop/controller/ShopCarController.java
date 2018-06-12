package cn.cn.wolfcode.shop.controller;

import cn.wolfcode.shop.controller.BaseController;
import cn.wolfcode.shop.service.IShoppingCarService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingcars")
public class ShopCarController extends BaseController{

    @Reference
    private IShoppingCarService shoppingCarService;

    @PostMapping
    public WSResponseVO addCar(String skuSn,Integer productNum,@RequestHeader("token") String token){
        shoppingCarService.add(skuSn,productNum,token);
        return success();
    }

}
