package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 杜承旭
 * @ClassNmae: CourseMarketRepository
 * @Description: TODO
 * @date 2019/10/26 9:00
 * @Version 1.0
 **/
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
