package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CmsTemplateService
 * @Description: TODO
 * @date 2019/10/12 8:50
 * @Version 1.0
 **/
public interface CmsTemplateService {

    /**
     * 查询模板信息列表
     * */
    QueryResponseResult findAllList();
}
