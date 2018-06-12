package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Product;
import cn.wolfcode.shop.domain.ProductSku;
import cn.wolfcode.shop.domain.ProductSkuProperty;
import cn.wolfcode.shop.domain.SkuProperty;
import cn.wolfcode.shop.service.IProductService;
import cn.wolfcode.shop.service.IProductSkuService;
import cn.wolfcode.shop.service.ISkuPropertyService;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductSkuVo;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.deploy.panel.IProperty;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productSku")
public class ProductSkuController extends BaseController{

    @Reference
    private IProductService productService;
    @Reference
    private ISkuPropertyService skuPropertyService;
    @Reference
    private IProductSkuService productSkuService;

    @RequestMapping
    public String index(Long productId, Map map){
        Product product = productService.getProductById(productId);
        List<ProductSku> productSkuList = productSkuService.queryByProductId(productId);
        if (CollectionUtils.isEmpty(productSkuList)){
            List<SkuProperty> skuPropertyList = skuPropertyService.queryPropertiesByCatalogId(product.getCatalog().getId());
            map.put("skuPropertyList",skuPropertyList);
            map.put("product",product);
            return "product_sku/generate_sku";
        }
        map.put("product",product);
        map.put("skuList",productSkuList);
        map.put("skuPropertyList",skuPropertyService.queryByProductId(productId));
        return "product_sku/sku_list";
    }

    @RequestMapping("getSkuPropertyValue")
    public String getSkuPropertyValue(Long skuPropertyId,Map map){
        SkuProperty skuProperty = skuPropertyService.getById(skuPropertyId);
        map.put("skuProperty",skuProperty);
        map.put("skuPropertyValueList",skuProperty.getValues());
        return "product_sku/sku_property_value_table";
    }

    @PostMapping("generateSku")
    public String generateSku(@RequestBody GenerateSkuVo vo,Map map){
        List<Map<String,Object>> skuList = skuPropertyService.generateSku(vo);
        List<SkuProperty> skuProList = vo.getSkuPropertyList();
        map.put("skuProList",skuProList);
        map.put("skuList",skuList);
        return "product_sku/sku_form";
    }
    /**
     * 保存sku
     */
    @RequestMapping(value = "save",method= RequestMethod.POST)
    @ResponseBody
    public WSResponseVO save(ProductSkuVo vo){
        skuPropertyService.save(vo);
        return success();
    }
}
