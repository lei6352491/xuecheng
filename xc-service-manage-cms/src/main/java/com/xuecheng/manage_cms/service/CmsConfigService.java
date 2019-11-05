package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.response.CmsConfigResult;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigService
 * @Description: TODO
 * @date 2019/10/14 9:15
 * @Version 1.0
 **/
public interface CmsConfigService {

    //根据主键id查询主页的配置信息
    CmsConfigResult findConfigById(String configId);
}
