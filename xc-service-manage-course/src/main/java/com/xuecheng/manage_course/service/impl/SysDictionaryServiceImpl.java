package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryOneResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_course.dao.SysDictionaryRepository;
import com.xuecheng.manage_course.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: SysDictionaryServiceImpl
 * @Description: TODO
 * @date 2019/10/24 17:12
 * @Version 1.0
 **/

@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    /**
     * 根据属性编号查询字典信息
     * */
    @Override
    public QueryResponseResult findDictByTypeCode(String typeCode) {
        SysDictionary sysDictionary = sysDictionaryRepository.findByDType(typeCode);
        if (sysDictionary == null)
            return null;
        QueryOneResult<SysDictionary> queryOneResult = new QueryOneResult<>();
        queryOneResult.setData(sysDictionary);
        return new QueryResponseResult(CommonCode.SUCCESS,queryOneResult);
    }
}
