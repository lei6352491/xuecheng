package com.xuecheng.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 杜承旭
 * @ClassNmae: ManageCmsClientAppication
 * @Description: TODO
 * @date 2019/10/23 10:41
 * @Version 1.0
 **/
@SpringBootApplication
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages={"com.xuecheng.framework.exception"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.xuecheng.manage_cms_client"})//扫描本项目下的所有类
public class ManageCmsClientAppication {

    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientAppication.class,args);
    }
}
