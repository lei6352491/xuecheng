package com.xuecheng.manage_course.service.impl;

import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import com.xuecheng.manage_course.dao.FileSystemRepository;
import com.xuecheng.manage_course.service.CoursePicService;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CoursePicServiceImpl
 * @Description: TODO
 * @date 2019/10/29 11:13
 * @Version 1.0
 **/
@Service
public class CoursePicServiceImpl implements CoursePicService {

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Value("${xuecheng.fastdfs.tracker_servers}")
    private String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    private String charset;

    @Override
    public ResponseResult addCoursePic(String courseId, String fileId) {
        if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(fileId))
            ExceptionCast.cast(CourseCode.COURSE_PARAM_ERROR);
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(fileId);
        coursePicRepository.save(coursePic);
        return ResponseResult.SUCCESS();
    }

    @Override
    public CoursePic findCoursePic(String courseId) {
        if (StringUtils.isEmpty(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PARAM_ERROR);
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);
        if (coursePicOptional.isPresent()) {
            CoursePic coursePic = coursePicOptional.get();
            return coursePic;
        }
        return null;
    }

    @Override
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        if (StringUtils.isEmpty(courseId))
            ExceptionCast.cast(CourseCode.COURSE_PARAM_ERROR);
        //查询图片信息
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);

        //判断图片信息是否存在
        if (!coursePicOptional.isPresent())
            ExceptionCast.cast(CourseCode.COURSE_PIC_ISNULL);
        CoursePic coursePic = coursePicOptional.get();

        //删除数据库中的课程图片信息
        coursePicRepository.delete(coursePic);

        //删除mongodb中的文件信息
        fileSystemRepository.deleteById(coursePic.getPic());

        //删除文件系统的图片信息
        StorageClient1 storageClient = createStorageClient();
        if (storageClient != null){
            try {
                storageClient.delete_file1(coursePic.getPic());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取fastDFS中storageClient对象
     * */
    private StorageClient1 createStorageClient(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
            //创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            return new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
            //初始化文件系统出错
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
        return null;
    }

}
