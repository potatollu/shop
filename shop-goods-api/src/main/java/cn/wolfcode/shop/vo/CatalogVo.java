package cn.wolfcode.shop.domain.vo;

import cn.wolfcode.shop.domain.Catalog;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CatalogVo extends Catalog{
    private Integer productCount;
    private Integer propertyCount;
}
