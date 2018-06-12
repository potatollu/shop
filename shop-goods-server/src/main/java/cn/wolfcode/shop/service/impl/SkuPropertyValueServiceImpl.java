package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.domain.SkuPropertyValue;
import cn.wolfcode.shop.mapper.PropertyValueMapper;
import cn.wolfcode.shop.mapper.SkuPropertyValueMapper;
import cn.wolfcode.shop.service.IPropertyValueService;
import cn.wolfcode.shop.service.ISkuPropertyValueService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkuPropertyValueServiceImpl implements ISkuPropertyValueService {

    @Autowired
    private SkuPropertyValueMapper propertyValueMapper;

    /**
     * 根据属性id查属性值
     * @param propertyId
     * @return
     */
    public List<SkuPropertyValue> selectByPropertyId(Long propertyId) {
        List<SkuPropertyValue> list = propertyValueMapper.selectByPropertyId(propertyId);
        return list;
    }

    /**
     * 保存属性值
     * @param propertyValues
     */
    public void savePropertiesValue(List<SkuPropertyValue> propertyValues) {
        propertyValues.forEach(propertyValue -> {
            if (propertyValue.getId() != null){
                propertyValueMapper.updateByPrimaryKey(propertyValue);
            }else {
                propertyValueMapper.insert(propertyValue);
            }
        });
    }

    public void delete(Long id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }
}
