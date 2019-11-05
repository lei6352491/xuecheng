package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author 杜承旭
 * @ClassNmae: QueryOneResult
 * @Description: TODO
 * @date 2019/10/24 13:59
 * @Version 1.0
 **/
@Data
@ToString
public class QueryOneResult<T> {

    private T data;

    public static <T> QueryOneResult<T> getQueryOneResult(T data){
        QueryOneResult<T> queryOneResult = new QueryOneResult<>();
        queryOneResult.setData(data);
        return queryOneResult;
    }
}
