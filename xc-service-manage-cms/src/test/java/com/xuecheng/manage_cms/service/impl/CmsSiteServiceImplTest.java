package com.xuecheng.manage_cms.service.impl;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CmsSiteServiceImplTest {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    public void uploadFileToGridFS() throws FileNotFoundException {
        File file = new File("C:\\Users\\lenovo\\Desktop\\tupian\\course.ftl");
        InputStream inputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(inputStream, "课程详情模板文件", "");
        System.out.println(objectId);
    }
}