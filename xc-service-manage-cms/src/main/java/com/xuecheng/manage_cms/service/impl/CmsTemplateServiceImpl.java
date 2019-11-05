package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.utils.ResponseResultUtil;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CmsTemplateServiceImpl
 * @Description: TODO
 * @date 2019/10/12 8:52
 * @Version 1.0
 **/

@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     * 查询模板信息列表
     * */
    @Override
    public QueryResponseResult findAllList() {
        List<CmsTemplate> all = cmsTemplateRepository.findAll();
        return ResponseResultUtil.getQueryResponseResult(all,new Long(all.size()));
    }
}
