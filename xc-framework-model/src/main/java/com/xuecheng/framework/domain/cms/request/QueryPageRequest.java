package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author 杜承旭
 * @ClassNmae: QueryPageRequest
 * @Description: TODO
 * @date 2019/10/10 14:50
 * @Version 1.0
 **/

@Data
@ToString
public class QueryPageRequest extends RequestData {

    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    // 模版id
    private String templateId;

}
