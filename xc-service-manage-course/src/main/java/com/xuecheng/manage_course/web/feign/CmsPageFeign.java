package com.xuecheng.manage_course.web.feign;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageFeign
 * @Description: TODO
 * @date 2019/11/7 13:33
 * @Version 1.0
 **/

@FeignClient(value = "xc-service-manage-cms")
public interface CmsPageFeign {

    /*@GetMapping(value = "/cms/page/get/{id}")
    CmsPageResult get(@PathVariable(value = "id") String pageId);*/

    @PostMapping(value = "/cms/page/add")
    CmsPageResult add(@RequestBody CmsPage cmsPage);

}
