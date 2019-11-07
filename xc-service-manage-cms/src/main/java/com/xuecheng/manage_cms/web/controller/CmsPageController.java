package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.GenerateHtmlResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageController
 * @Description: TODO
 * @date 2019/10/10 15:20
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    /**
     * 多条件查询页面信息
     * */
    @Override
    @GetMapping(value = "/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable(value = "page") int page,@PathVariable(value = "size") int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page,size,queryPageRequest);
    }

    /**
     * 添加页面信息
     * */
    @Override
    @PostMapping(value = "/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    /**
     * 根据主键查询cms页面信息
     * */
    @Override
    @GetMapping(value = "/get/{id}")
    public CmsPageResult get(@PathVariable(value = "id") String pageId) {
        return cmsPageService.get(pageId);
    }

    /**
     * 修改页面信息
     * */
    @Override
    @PutMapping(value = "/edit")
    public ResponseResult update(@RequestBody CmsPage cmsPage) {
        return cmsPageService.update(cmsPage);
    }

    /**
     * 删除cms页面信息
     * */
    @Override
    @DeleteMapping(value = "/del/{id}")
    public ResponseResult delete(@PathVariable(value = "id") String pageId) {
        return cmsPageService.delete(pageId);
    }

    /**
     * 静态化
     * */
    @Override
    @GetMapping(value = "/getHtml/{id}")
    public GenerateHtmlResult getPageHtml(@PathVariable(value = "id") String pageId) {
        return cmsPageService.getPageHtml(pageId);
    }

    @Override
    @GetMapping(value = "/generateHtml/{id}")
    public ResponseResult generateHtml(@PathVariable(value = "id") String pageId) {
        return cmsPageService.saveGenerateHtml(pageId);
    }

    /**
     * 发布页面
     * */
    @Override
    @GetMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable(value = "pageId") String pageId) {
        return cmsPageService.postPage(pageId);
    }
}
