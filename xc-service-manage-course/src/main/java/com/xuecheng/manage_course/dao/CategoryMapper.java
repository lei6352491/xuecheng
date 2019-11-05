package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 杜承旭
 * @ClassNmae: CategoryMapper
 * @Description: TODO
 * @date 2019/10/25 13:38
 * @Version 1.0
 **/
@Mapper
public interface CategoryMapper {

    CategoryNode selectAllCategoryNode();
}
