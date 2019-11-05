package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author 杜承旭
 * @ClassNmae: ExceptionCast
 * @Description: TODO
 * @date 2019/10/12 16:08
 * @Version 1.0
 **/
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
