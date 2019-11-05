package com.xuecheng.manage_cms_client.service;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageService
 * @Description: TODO
 * @date 2019/10/23 10:55
 * @Version 1.0
 **/
public interface CmsPageService {

    //将静态化页面发送到服务器路劲下
    public void saveHtmlToServicePath(String pageId);
}
