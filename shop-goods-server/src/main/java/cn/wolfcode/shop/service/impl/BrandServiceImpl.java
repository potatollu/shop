package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Brand;
import cn.wolfcode.shop.mapper.BrandMapper;
import cn.wolfcode.shop.service.IBrandService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by leo on 2018/2/14.
 */
@Service
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getAllBrand() {
        return brandMapper.selectAll();
    }
}
