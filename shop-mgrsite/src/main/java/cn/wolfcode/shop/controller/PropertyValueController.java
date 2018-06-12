package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("propertyValue")
public class PropertyValueController extends BaseController{

    @Reference
    private IPropertyValueService propertyValueService;

    /**
     *分类属性值列表
     */
    @RequestMapping
    public String list(@ModelAttribute("propertyId") Long propertyId, Map map){
        List<PropertyValue> propertyValueList =  propertyValueService.selectByPropertyId(propertyId);
        map.put("propertyValueList",propertyValueList);
        return "property/property_value_list";
    }

    /**
     * 保存分类属性
     */
    @RequestMapping(method= RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public WSResponseVO saveOrUpdate(@RequestBody List<PropertyValue> propertyValues){
        propertyValueService.savePropertiesValue(propertyValues);
        return success();
    }
    /**
     * 删除属性值
     */
    @RequestMapping(value = "{id}",method=RequestMethod.GET)
    @ResponseBody
    public WSResponseVO delete(@PathVariable("id")Long id){
        propertyValueService.delete(id);
        return success();
    }
}
