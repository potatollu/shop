package cn.wolfcode.shop.vo;

import cn.wolfcode.shop.domain.ProductSku;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProductSkuVo implements Serializable{
    private Long productId;
    private String sn;
    private BigDecimal price;
    private List<ProductSku> productSkuList = new ArrayList<ProductSku>();
}
