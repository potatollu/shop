package cn.wolfcode.shop.qo;

import lombok.Getter;

/**
 * Created by leo on 2018/2/13.
 */
@Getter
public class ProductQueryObject extends QueryObject {

    private String keyword;

    public void setKeyword(String keyword) {
        if (keyword != null && keyword.trim().length() > 0) {
            this.keyword = "%" + keyword + "%";
        }
    }

}
