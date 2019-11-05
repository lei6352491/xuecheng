package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigResult
 * @Description: TODO
 * @date 2019/10/14 9:25
 * @Version 1.0
 **/

@Data
@ToString
public class CmsConfigResult extends ResponseResult implements Serializable {
    CmsConfig cmsConfig;
    public CmsConfigResult(ResultCode resultCode, CmsConfig cmsConfig){
        super(resultCode);
        this.cmsConfig = cmsConfig;
    }

}
