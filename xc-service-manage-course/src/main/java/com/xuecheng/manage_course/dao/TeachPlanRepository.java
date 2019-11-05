package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 杜承旭
 * @ClassNmae: TeachPlanRepository
 * @Description: TODO
 * @date 2019/10/24 15:18
 * @Version 1.0
 **/
public interface TeachPlanRepository extends JpaRepository<Teachplan,String> {

    Teachplan findByCourseidAndParentid(String courseId,String parentId);
}
