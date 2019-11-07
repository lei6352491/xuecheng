package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.GenerateHtmlResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.Response;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageControllerApi
 * @Description: TODO
 * @date 2019/10/10 15:18
 * @Version 1.0
 **/
public interface CmsPageControllerApi {

    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) ;

    CmsPageResult add(CmsPage cmsPage);

    CmsPageResult get(String id);

    ResponseResult update(CmsPage cmsPage);

    ResponseResult delete(String pageId);

    GenerateHtmlResult getPageHtml(String pageId);

    ResponseResult generateHtml(String pageId);

    ResponseResult post(String pageId);

}
