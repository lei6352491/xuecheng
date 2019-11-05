package com.xuecheng.api.sysdict;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.ApiOperation;

/**
 * @author 杜承旭
 * @ClassNmae: SysdictionaryControllerApi
 * @Description: TODO
 * @date 2019/10/25 9:05
 * @Version 1.0
 **/

public interface SysdictionaryControllerApi {

    @ApiOperation(value = "根据状态信息查询字典信息")
    QueryResponseResult findDict(String typeCode);
}
