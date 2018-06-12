package cn.wolfcode.shop.controller;

import cn.wolfcode.shop.domain.Catalog;
import cn.wolfcode.shop.domain.vo.CatalogVo;
import cn.wolfcode.shop.service.ICatalogService;
import cn.wolfcode.shop.vo.WSResponseVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController extends BaseController {

    @Reference
    private ICatalogService catalogService;

    /**
     * 首页
     *
     * @return
     */
    @GetMapping
    public String index() {
        return "catalog/catalog";
    }

    /**
     * 菜单列表
     *
     * @param id
     * @return
     */
    @GetMapping("/children")
    @ResponseBody
    public List<CatalogVo> children(Long id) {
        List<CatalogVo> catalogs = catalogService.selectByParentId(id);
        return catalogs;
    }

    /**
     * 分类 列表
     *
     * @param id
     * @param map
     * @return
     */
    @GetMapping(value = "/children", produces = "text/html")
    public String childrenForHtml(Long id, Map map) {
        List<CatalogVo> catalogs = catalogService.selectByParentId(id);
        map.put("catalogList", catalogs);
        return "catalog/children";
    }

    /**
     * 保存按钮
     */
    @PostMapping
    @ResponseBody
    public WSResponseVO saveOrUpdate(Catalog catalog) {
        catalogService.saveOrUpdate(catalog);
        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("{id}")
    @ResponseBody
    public WSResponseVO delete(@PathVariable("id") Long id) {
        catalogService.deleteById(id);
        return success();
    }

    /**
     * 排序
     */
    @PatchMapping(consumes = "application/json")
    @ResponseBody
    public WSResponseVO updateSort(@RequestBody Long[] ids) {
        catalogService.updateSort(ids);
        return success();
    }
}
