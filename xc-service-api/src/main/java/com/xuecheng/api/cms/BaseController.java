package com.xuecheng.api.cms;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 杜承旭
 * @ClassNmae: BaseController
 * @Description: TODO
 * @date 2019/10/16 14:02
 * @Version 1.0
 **/
public interface BaseController {

    void preview(String pageId, HttpServletResponse response);
}
