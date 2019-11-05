package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigControllerApi
 * @Description: TODO
 * @date 2019/10/14 9:08
 * @Version 1.0
 **/
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
    CmsConfigResult getCmsConfig(String configId);
}
