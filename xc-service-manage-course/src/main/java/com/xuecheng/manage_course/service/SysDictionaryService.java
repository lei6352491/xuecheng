package com.xuecheng.manage_course.service;

import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 * @author 杜承旭
 * @ClassNmae: SysDictionaryService
 * @Description: TODO
 * @date 2019/10/24 17:11
 * @Version 1.0
 **/
public interface SysDictionaryService {

    QueryResponseResult findDictByTypeCode(String typeCode);
}
