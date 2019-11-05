package com.xuecheng.framework.utils;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;

import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: ResponseResultUtil
 * @Description: TODO
 * @date 2019/10/11 9:18
 * @Version 1.0
 **/
public class ResponseResultUtil {

    /**
     * 查询成功返回的结果对象
     * */
    public static <T> QueryResponseResult getQueryResponseResult(List<T> list,Long total){
        QueryResult<T> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
