package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePublishResult
 * @Description: TODO
 * @date 2019/11/7 9:21
 * @Version 1.0
 **/

@Data
@ToString
public class CoursePublishResult extends ResponseResult {

    private String previewUrl;

    public CoursePublishResult(ResultCode resultCode,String previewUrl){
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
