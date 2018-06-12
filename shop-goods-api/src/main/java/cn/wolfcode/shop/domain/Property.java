package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Property extends BaseDomain{

    /**
     * 属性输入框
     */
    public static final int PROPERTY_TYPE_INPUT = 0;
    /**
     * 属性下拉框
     */
    public static final int PROPERTY_TYPE_SELECT = 1;

    private Long catalogId;

    private String name;

    private Integer sort;

    private Integer type;

    private List<PropertyValue> values = new ArrayList<PropertyValue>();

    @JSONField(serialize = false)
    public String getJson(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",getId());
        map.put("catalogId",catalogId);
        map.put("name",name);
        map.put("sort",sort);
        map.put("type",type);
        return JSON.toJSONString(map);
    }

}