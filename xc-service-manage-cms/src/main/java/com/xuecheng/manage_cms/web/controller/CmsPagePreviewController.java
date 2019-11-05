package com.xuecheng.manage_cms.web.controller;


import com.xuecheng.api.cms.BaseController;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPagePreviewController
 * @Description: TODO
 * @date 2019/10/16 14:03
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/cms")
public class CmsPagePreviewController implements BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping(value = "preview/{id}")
    public void preview(@PathVariable(value = "id") String pageId, HttpServletResponse response) {
        String html = cmsPageService.getStaticFileData(pageId);
        if (StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_COURSE_PERVIEWISNULL);
        }
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(html.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
