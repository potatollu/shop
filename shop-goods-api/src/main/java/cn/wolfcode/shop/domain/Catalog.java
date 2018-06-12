package cn.wolfcode.shop.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class Catalog extends BaseDomain{

    private String name;

    private String sn;

    private Integer sort;

    private Long parentId;

    private Boolean isParent;

    public String getJson(){
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("id",getId());
        map.put("name",name);
        map.put("sn",sn);
        map.put("sort",sort);
        map.put("parentId",parentId);
        map.put("isParent",isParent);
        return JSON.toJSONString(map);
    }

}