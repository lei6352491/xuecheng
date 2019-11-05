package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageRepository
 * @Description: TODO
 * @date 2019/10/10 15:30
 * @Version 1.0
 **/
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    /**
     * 自定义方法
     * 根据页面名称，站点id，页面访问路劲，查询是否存在该页面信息
     * */
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
