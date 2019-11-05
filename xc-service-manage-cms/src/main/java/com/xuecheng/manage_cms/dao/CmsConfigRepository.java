package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CmsConfigRepository
 * @Description: TODO
 * @date 2019/10/14 9:15
 * @Version 1.0
 **/

public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
