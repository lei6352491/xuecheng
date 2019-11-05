package com.xuecheng.manage_cms_client.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.manage_cms_client.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageServiceImpl
 * @Description: TODO
 * @date 2019/10/23 10:55
 * @Version 1.0
 **/
@Service
public class CmsPageServiceImpl implements CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     *发布页面信息到文件路劲
     * */
    @Override
    public void saveHtmlToServicePath(String pageId) {
        //获取cms页面信息
        Optional<CmsPage> pageOptional = cmsPageRepository.findById(pageId);
        if (!pageOptional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_NOPAGEDATA);
        }
        CmsPage cmsPage = pageOptional.get();

        //获取站点信息
        if (StringUtils.isEmpty(cmsPage.getSiteId())){
            ExceptionCast.cast(CmsCode.CMS_COURSE_SITEIDISNULL);
        }
        Optional<CmsSite> siteOptional = cmsSiteRepository.findById(cmsPage.getSiteId());
        if (!siteOptional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_COURSE_SITEDATAISNULL);
        }
        CmsSite cmsSite = siteOptional.get();

        //获取文件的物理路径
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cmsSite.getPagePhysicalPath()).append(cmsPage.getPagePhysicalPath()).append(cmsPage.getPageName());

        //从gridFs中获取静态页面
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsPage.getHtmlFileId())));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        InputStream inputStream = null;
        try {
            inputStream = gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把静态文件写入到文件系统的物理路径下
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(stringBuffer.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null && fileOutputStream != null){
            try {
                IOUtils.copy(inputStream,fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
