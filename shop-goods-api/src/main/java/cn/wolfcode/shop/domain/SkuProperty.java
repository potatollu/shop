package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class SkuProperty extends BaseDomain{

    private Long catalogId;

    private String name;

    private Byte type;

    private Integer sort;

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