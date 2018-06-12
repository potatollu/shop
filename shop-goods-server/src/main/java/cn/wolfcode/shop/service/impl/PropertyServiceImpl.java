package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Property;
import cn.wolfcode.shop.mapper.PropertyMapper;
import cn.wolfcode.shop.service.IPropertyService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PropertyServiceImpl implements IPropertyService{

    @Autowired
    private PropertyMapper propertyMapper;

    /**
     * 根据分类id查找属性
     * @param catalogId
     * @return
     */
    public List<Property> queryByCataId(Long catalogId) {
        List<Property> properties = propertyMapper.queryByCataId(catalogId);
        return properties;
    }

    /**
     * 保存跟新属性
     * @param property
     */
    public void saveOrUpdate(Property property) {
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
}
