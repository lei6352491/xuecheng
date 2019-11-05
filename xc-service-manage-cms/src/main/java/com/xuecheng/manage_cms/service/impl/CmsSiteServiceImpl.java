package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsSiteServer;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.utils.ResponseResultUtil;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杜承旭
 * @ClassNmae: CmsSiteServiceImpl
 * @Description: TODO
 * @date 2019/10/11 15:07
 * @Version 1.0
 **/
@Service
public class CmsSiteServiceImpl implements CmsSiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Override
    public QueryResponseResult findAllList() {
        List<CmsSite> all = cmsSiteRepository.findAll();
        return ResponseResultUtil.getQueryResponseResult(all,new Long(all.size()));
    }
}
