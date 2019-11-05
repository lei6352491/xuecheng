package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigController
 * @Description: TODO
 * @date 2019/10/14 9:10
 * @Version 1.0
 **/

@Controller
@RequestMapping(value = "/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public CmsConfigResult getCmsConfig(@PathVariable(value = "id") String configId) {
        return cmsConfigService.findConfigById(configId);
    }

    @RequestMapping("/banner")
    public String banner(Map<String,Object> freeMarker){
        ResponseEntity<Map> entity =
                restTemplate.getForEntity
                        ("http://localhost:31001/cms/config/get/5a791725dd573c3574ee333f", Map.class);
        Map map = (Map) entity.getBody().get("cmsConfig");
        freeMarker.putAll(map);
        return "index_banner";
    }
}
