package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.GenerateHtmlResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageService
 * @Description: TODO
 * @date 2019/10/11 8:43
 * @Version 1.0
 **/
public interface CmsPageService {

    //多条件分页查询cms页面信息
    QueryResponseResult findList(Integer page, Integer size, QueryPageRequest queryPageRequest);

    //新增cms页面信息
    QueryResponseResult add(CmsPage cmsPage);

    //根据主键查询cms页面信息
    CmsPageResult get(String pageId);

    //修改页面信息
    ResponseResult update(CmsPage cmsPage);

    //删除页面信息
    ResponseResult delete(String pageId);

    //生成静态化页面
    GenerateHtmlResult getPageHtml(String pageId);

    //保存静态化页面
    ResponseResult saveGenerateHtml(String pageId);

    //获取静态文件数据
    String getStaticFileData(String pageId);

    //发布页面
    ResponseResult postPage(String pageId);
}
