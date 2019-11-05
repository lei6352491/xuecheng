package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @author 杜承旭
 * @ClassNmae: CustomException
 * @Description: TODO
 * @date 2019/10/12 15:45
 * @Version 1.0
 **/
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super("错误代码："+resultCode.code()+"错误信息："+resultCode.message());
        this.resultCode = resultCode;
    }

    public  ResultCode getResultCode(){
        return resultCode;
    }
}
