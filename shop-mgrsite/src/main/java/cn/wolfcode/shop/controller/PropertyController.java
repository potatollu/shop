package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.service.IPropertyService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("properties")
@Controller
public class PropertyController extends BaseController {

    @Reference
    private IPropertyService propertyService;

    /**
     * 首页
     * @return
     */
    @RequestMapping
    public String index() {
        return "property/property";
    }

    /**
     * 属性列表
     */
    @RequestMapping("/list")
    public String properties(Long catalogId, Map map) {
        List<Property> properties = propertyService.queryByCataId(catalogId);
        map.put("properties", properties);
        return "property/property_list";
    }
    /**
     * 新增和编辑属性
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public WSResponseVO saveOrUpdate(Property property){
        propertyService.saveOrUpdate(property);
        return success();
    }
    /**
     * 属性删除
     */
    @RequestMapping(value = "{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public WSResponseVO delete(@PathVariable("id") Long id){
        propertyService.delete(id);
        return success();
    }
}
