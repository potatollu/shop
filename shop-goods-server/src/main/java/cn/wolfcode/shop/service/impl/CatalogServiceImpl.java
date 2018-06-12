package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.vo.CatalogVo;
import cn.wolfcode.shop.mapper.CatalogMapper;
import cn.wolfcode.shop.service.ICatalogService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public List<Catalog> listAll() {
        return catalogMapper.selectAll();
    }

    /**
     * 根据父类id查找子类
     *
     * @param id
     * @return
     */
    public List<CatalogVo> selectByParentId(Long id) {
        List<Catalog> catalogs = catalogMapper.selectByParentId(id);
        //使用linkedList插入快
        List<CatalogVo> catalogVo = new LinkedList<>();
        //遍历集合,统计每个商品对应的商品数和属性数
        catalogs.forEach(catalog->{//catalog是遍历出来的每一条
            //key->catalog:1 value->{productCount:1,propertyCount:1}
            HashOperations<String,String,Integer> hash = redisTemplate.opsForHash();
            Map<String, Integer> entries = hash.entries("catalog:" + catalog.getId());
            Integer productCount = 0;
            Integer propertyCount = 0;
            //如果缓存中没有,就从数据库中计算出来放进缓存里
            if (entries == null || entries.size() == 0){
                //商品个数
                productCount = catalogMapper.selectProductCountByCatalogId(catalog.getId());
                propertyCount = catalogMapper.selectPropertyCountByCatalogId(catalog.getId());

            }else {
                //如果缓存中已经有了,直接从redis缓存中取
                productCount = entries.get("productCount");
                propertyCount = entries.get("propertyCount");
            }
            //将统计出的数量设置进分类中
            CatalogVo vo = new CatalogVo();
            BeanUtils.copyProperties(catalog,vo);
            vo.setProductCount(productCount);
            vo.setPropertyCount(propertyCount);
            catalogVo.add(vo);
        });
        return catalogVo;
    }

    /**
     * 保存跟新
     *
     * @param catalog
     */
    public void saveOrUpdate(Catalog catalog) {
        if (catalog.getId() == null) {
            //获取父分类
            Catalog parent = null;
            if (catalog.getParentId() != null){
                parent = catalogMapper.selectByPrimaryKey(catalog.getParentId());
            }
            //如果存在,就把它的isParent改为1,并且跟新数据
            if (parent != null && !parent.getIsParent()){
                parent.setIsParent(true);
                catalogMapper.updateByPrimaryKey(parent);
            }
            //保存自己
            catalogMapper.insert(catalog);

        } else {
            //跟新
            catalogMapper.updateByPrimaryKey(catalog);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(Long id) {
        if (id != null){
            //判断该父分类下还有没有子分类
            Long num = catalogMapper.getParentChildrenById(id);
            //如果只有一条了,先把原来父类的isParent改为false
            if (num == 1){
                //创建一个新的catalog对象,设置id,跟新到它对应的对象
                Catalog parent = new Catalog();
                parent.setIsParent(false);
                Catalog catalog = catalogMapper.selectByPrimaryKey(id);
                parent.setId(catalog.getParentId());
                catalogMapper.updateByPrimaryKey(parent);
            }
            //删除
            catalogMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 排序
     * @param ids
     */
    public void updateSort(Long[] ids) {
        if (ids != null){
            Catalog catalog;
            for (int i = 0; i < ids.length; i++) {
                Long id = ids[i];
                catalog = new Catalog();
                catalog.setId(id);
                catalog.setSort(i+1);
                catalogMapper.updateByPrimaryKey(catalog);
            }
        }
    }

    public Integer selectProductCountByCatalogId(Long id) {
        return catalogMapper.selectProductCountByCatalogId(id);
    }

    public Integer selectPropertyCountByCatalogId(Long id) {
        return catalogMapper.selectPropertyCountByCatalogId(id);
    }

    public List<Catalog> queryParentByCatalogId(Long catalogId) {
        return catalogMapper.queryParentByCatalogId(catalogId);
    }
}
