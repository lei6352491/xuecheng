package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageRepository
 * @Description: TODO
 * @date 2019/10/23 10:52
 * @Version 1.0
 **/

public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

}
