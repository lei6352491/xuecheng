package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CmsTemplateRepository
 * @Description: TODO
 * @date 2019/10/12 8:45
 * @Version 1.0
 **/
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {

}
