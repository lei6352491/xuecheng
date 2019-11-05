package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsConfigResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigServiceImpl
 * @Description: TODO
 * @date 2019/10/14 9:18
 * @Version 1.0
 **/

@Service
public class CmsConfigServiceImpl implements CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Override
    //根据主键id查询主页的配置信息
    public CmsConfigResult findConfigById(String configId) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(configId);
        if (optional.isPresent()){
            CmsConfig cmsConfig = optional.get();
            return new CmsConfigResult(CommonCode.SUCCESS,cmsConfig);
        }
        return new CmsConfigResult(CommonCode.NODATA,null);
    }
}
