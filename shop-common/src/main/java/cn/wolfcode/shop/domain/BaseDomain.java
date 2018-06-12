package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseDomain implements Serializable{
    private Long id;
}
