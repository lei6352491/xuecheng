package com.xuecheng.manage_course.web.controller;

import com.xuecheng.api.sysdict.SysdictionaryControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杜承旭
 * @ClassNmae: SysDictIionaryController
 * @Description: TODO
 * @date 2019/10/25 9:04
 * @Version 1.0
 **/

@RestController
@RequestMapping(value = "/sys/dictionary")
public class SysDictIionaryController implements SysdictionaryControllerApi {

    @Resource(name = "sysDictionaryServiceImpl")
    private SysDictionaryService sysDictionaryService;

    /**
     * 根据状态编号查询字典信息
     * */
    @Override
    @GetMapping(value = "/get/{code}")
    public QueryResponseResult findDict(@PathVariable(value = "code") String typeCode) {
        return sysDictionaryService.findDictByTypeCode(typeCode);
    }
}
