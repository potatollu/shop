package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.service.ISkuPropertyService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("skuProperties")
@Controller
public class SkuPropertyController extends BaseController {

    @Reference
    private ISkuPropertyService skuPropertyService;
    @Reference
    private IProductService productService;

    /**
     * 首页
     * @return
     */
    @RequestMapping
    public String index() {
        return "sku_property/property";
    }

    /**
     * 属性列表
     */
    @RequestMapping("/list")
    public String properties(Long catalogId, Map map) {
        List<SkuProperty> properties = skuPropertyService.queryByCataId(catalogId);
        map.put("properties", properties);
        return "sku_property/property_list";
    }
    /**
     * 新增和编辑属性
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public WSResponseVO saveOrUpdate(SkuProperty property){
        skuPropertyService.saveOrUpdate(property);
        return success();
    }
    /**
     * 属性删除
     */
    @RequestMapping(value = "{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public WSResponseVO delete(@PathVariable("id") Long id){
        skuPropertyService.delete(id);
        return success();
    }
}
