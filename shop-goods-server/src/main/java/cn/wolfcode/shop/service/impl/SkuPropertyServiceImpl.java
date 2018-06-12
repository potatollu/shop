package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.*;
import cn.wolfcode.shop.mapper.ProductMapper;
import cn.wolfcode.shop.mapper.ProductSkuMapper;
import cn.wolfcode.shop.mapper.ProductSkuPropertyMapper;
import cn.wolfcode.shop.mapper.SkuPropertyMapper;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.service.ISkuPropertyService;
import cn.wolfcode.shop.util.DescartesUtils;
import cn.wolfcode.shop.vo.GenerateSkuVo;
import cn.wolfcode.shop.vo.ProductSkuVo;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuPropertyServiceImpl implements ISkuPropertyService {

    @Autowired
    private SkuPropertyMapper propertyMapper;
    @Autowired
    private ICatalogService catalogService;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductSkuPropertyMapper productSkuPropertyMapper;
    @Autowired
    private SkuPropertyMapper skuPropertyMapper;
    /**
     * 根据分类id查找属性
     * @param catalogId
     * @return
     */
    public List<SkuProperty> queryByCataId(Long catalogId) {
        List<SkuProperty> properties = propertyMapper.queryByCataId(catalogId);
        return properties;
    }

    /**
     * 保存跟新属性
     * @param property
     */
    public void saveOrUpdate(SkuProperty property) {
        if(property.getId() != null){
            propertyMapper.updateByPrimaryKey(property);
        }else {
            propertyMapper.insert(property);
        }
    }

    /**
     * 删除属性
     * @param id
     */
    public void delete(Long id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    public List<SkuProperty> queryPropertiesByCatalogId(Long id) {
        return propertyMapper.queryPropertiesByCatalogId(id);
    }

    public SkuProperty getById(Long skuPropertyId) {
        return propertyMapper.selectByPrimaryKey(skuPropertyId);
    }

    /**
     * 生成sku
     * @param vo
     */
    public List<Map<String,Object>> generateSku(GenerateSkuVo vo) {
        //用来装属性
        List<SkuProperty> skuPropertyList = vo.getSkuPropertyList();
        //用来装属性值
        List<SkuPropertyValue> skuPropertyValueList = vo.getSkuPropertyValueList();
        Product product = vo.getProduct();
        //1.生成sku编号
        String skuSn = this.genSkuSn(product);
        //2.转换前端传过来的数据变成sku需要的数据
        List<List<SkuPropertyValue>> datas = this.convertDate(skuPropertyList,skuPropertyValueList);
        //3.使用准备好的数据,生成sku
        List<List<SkuPropertyValue>> skuList = DescartesUtils.descart(datas);
        //根据生成的sku数据,组装完整的sku
        //[32G,黑],[64G,黑]
        List<Map<String,Object>> results = new LinkedList<>();
        for (int i = 0; i < skuList.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            results.add(map);
            map.put("sn",skuSn+(i+1));
            map.put("price",product.getBasePrice());
            //[32G,黑]
            List<SkuPropertyValue> skuValues = skuList.get(i);

            skuValues.forEach(value ->{
                map.put(value.getPropertyId()+"",value.getValue());
            });
        }
        return results;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ProductSkuVo vo) {
        Long productId = vo.getProductId();
        //保存productSku
        List<ProductSku> productSkuList = vo.getProductSkuList();
        productSkuList.forEach(productSku -> {
            String sn = productSku.getSn();
            if (!StringUtils.isEmpty(sn)){
                productSku.setProductId(productId);
                //保存sku
                productSkuMapper.insert(productSku);
                //保存skuPropertyValue
                productSku.getProductSkuPropertyList().forEach(psp->{
                    //关联productsku对象
                    psp.setProductSkuId(productSku.getId());
                    productSkuPropertyMapper.insert(psp);
                });
            }

        });
    }

    public List<SkuProperty> queryByProductId(Long productId) {
        return skuPropertyMapper.selectByProductId(productId);
    }

    /**
     * 将获取的sku属性值,转换为生成sku需要的数据
     * @param skuPropertyList
     * @param skuPropertyValueList
     * @return
     */
    private List<List<SkuPropertyValue>> convertDate(List<SkuProperty> skuPropertyList, List<SkuPropertyValue> skuPropertyValueList) {

        List<List<SkuPropertyValue>> datas = new ArrayList<>(skuPropertyList.size());
        //遍历属性
        skuPropertyList.forEach(property ->{
            //每一属性对应的属性值列表
            List<SkuPropertyValue> data = new ArrayList<>();
            datas.add(data);
            //遍历属性值,添加进属性中
            skuPropertyValueList.forEach(value->{
                if(property.getId().equals(value.getPropertyId())){
                    data.add(value);
                }
            });
        });
        return datas;
    }

    private String genSkuSn(Product product) {
        StringBuilder sb = new StringBuilder(50);
        Long id = product.getCatalog().getId();
        //获取当前商品对应的所有父分类
        List<Catalog> parents = catalogService.queryParentByCatalogId(id);
        if(!CollectionUtils.isEmpty(parents)){
            for (int i = 0; i < parents.size(); i++) {
                Catalog catalog = parents.get(i);
                if (i == 0){
                    //处理一级分类
                    sb.append(catalog.getSn().substring(0,2));
                }else {
                    //处理二级和三级分类
                    Integer sort = catalog.getSort();
                    String sn = sort < 10 ? "0" + sort :sort + "";
                    sb.append(sn);
                }
            }
        }
        return sb.toString();
    }
}
