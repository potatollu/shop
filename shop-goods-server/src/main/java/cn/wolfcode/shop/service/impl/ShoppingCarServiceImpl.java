package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Car;
import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.mapper.CarMapper;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.service.IShoppingCarService;
import cn.wolfcode.shop.util.AssertUtil;
import cn.wolfcoe.shop.domain.UserLogin;
import cn.wolfcoe.shop.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingCarServiceImpl implements IShoppingCarService{

    @Reference
    private IUserService userService;
    @Autowired
    private IProductSkuService productSkuService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private IProductService productService;

    @Transactional(rollbackFor = Exception.class)
    public void add(String skuSn, Integer productNum, String token) {
        //根据用户id和商品的skuId查找购物车中是否已经有这一条记录,没有就添加购物车记录
        //所以要获取到当前登录用户的id和sku
        //根据token获取当前登录用户
        UserLogin userLogin = userService.getByToken(token);
        AssertUtil.isNull(userLogin,"亲,你还没登录~");
        //根据skuSn获取sku_id
        ProductSku sku = productSkuService.queryByProductSn(skuSn);
        //比较购物车中的数据
        Car carInDB = carMapper.selectByUserIdAndSkuId(userLogin.getId(),sku.getId());
        if (carInDB != null){
           carInDB.setProductNumber(carInDB.getProductNumber() + productNum);
           carMapper.updateByPrimaryKey(carInDB);
        }else {
            Product product = productService.getProductById(sku.getProductId());
            Car car = new Car();
            car.setProductNumber(productNum);
            car.setUserId(userLogin.getId());
            car.setSkuId(sku.getId());
            car.setProductImg(product.getImage());
            car.setProductName(product.getName());
            carMapper.insert(car);
        }
    }

    public Car getById(Long id) {
        return carMapper.selectByPrimaryKey(id);
    }

    public void delectByCarId(Long id) {
        carMapper.deleteByPrimaryKey(id);
    }

}
