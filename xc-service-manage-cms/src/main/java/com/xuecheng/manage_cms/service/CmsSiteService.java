package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: CmsSiteService
 * @Description: TODO
 * @date 2019/10/11 15:05
 * @Version 1.0
 **/
public interface CmsSiteService {
    /**
     * 查询所有站点
     * */
    QueryResponseResult findAllList();
}
