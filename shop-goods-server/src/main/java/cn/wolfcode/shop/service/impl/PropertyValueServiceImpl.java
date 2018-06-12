package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.PropertyValue;
import cn.wolfcode.shop.mapper.PropertyValueMapper;
import cn.wolfcode.shop.service.IPropertyValueService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements IPropertyValueService{

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    /**
     * 根据属性id查属性值
     * @param propertyId
     * @return
     */
    public List<PropertyValue> selectByPropertyId(Long propertyId) {
        List<PropertyValue> list = propertyValueMapper.selectByPropertyId(propertyId);
        return list;
    }

    /**
     * 保存属性值
     * @param propertyValues
     */
    public void savePropertiesValue(List<PropertyValue> propertyValues) {
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
