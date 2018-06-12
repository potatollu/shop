package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.domain.SkuPropertyValue;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.service.ISkuPropertyValueService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("skuPropertyValue")
public class SkuPropertyValueController extends BaseController{

    @Reference
    private ISkuPropertyValueService skuPropertyValueService;

    /**
     *分类属性值列表
     */
    @RequestMapping
    public String list(@ModelAttribute("propertyId") Long propertyId, Map map){
        List<SkuPropertyValue> propertyValueList =  skuPropertyValueService.selectByPropertyId(propertyId);
        map.put("propertyValueList",propertyValueList);
        return "sku_property/property_value_list";
    }

    /**
     * 保存分类属性
     */
    @RequestMapping(method= RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public WSResponseVO saveOrUpdate(@RequestBody List<SkuPropertyValue> propertyValues){
        skuPropertyValueService.savePropertiesValue(propertyValues);
        return success();
    }
    /**
     * 删除属性值
     */
    @RequestMapping(value = "{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public WSResponseVO delete(@PathVariable("id")Long id){
        skuPropertyValueService.delete(id);
        return success();
    }
}
