package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 杜承旭
 * @ClassNmae: SysDictionaryRepository
 * @Description: TODO
 * @date 2019/10/24 17:10
 * @Version 1.0
 **/
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {

    SysDictionary findByDType(String typeCode);

}
