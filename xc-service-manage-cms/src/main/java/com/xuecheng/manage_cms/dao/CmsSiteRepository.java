package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CmsSiteRepository
 * @Description: TODO
 * @date 2019/10/11 15:02
 * @Version 1.0
 **/
public interface CmsSiteRepository extends MongoRepository<CmsSite,String>{
}
