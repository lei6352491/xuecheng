package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFSInputFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.GenerateHtmlResult;
import com.xuecheng.framework.domain.course.CourseView;
import com.xuecheng.framework.domain.course.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.framework.utils.CreateBaenUtil;
import com.xuecheng.framework.utils.EmptyStringToNullUtil;
import com.xuecheng.framework.utils.PagingUtil;
import com.xuecheng.framework.utils.ResponseResultUtil;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.omg.CORBA.MARSHAL;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageServiceImpl
 * @Description: TODO
 * @date 2019/10/11 8:45
 * @Version 1.0
 **/
@Service
public class CmsPageServiceImpl implements CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitmqConfig rabbitmqConfig;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 多条件分页查询cms页面信息
     */
    @Override
    public QueryResponseResult findList(Integer page, Integer size, QueryPageRequest queryPageRequest) {
        //分页数据初始化
        Integer[] array = PagingUtil.pagingInitialization(page, size);
        //判断页面查询对象是否为空,若为空构造一个对象
        CreateBaenUtil.getCreateBean(queryPageRequest);
        //创建分页对象
        Pageable pageable = new PageRequest(array[0], array[1]);
        //设置匹配规则
        ExampleMatcher exampleMatcher =
                ExampleMatcher.matching().withMatcher
                        ("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建条件查询对象
        CmsPage cmsPage = new CmsPage();
        //把javaBean中空字符串替换成null
        EmptyStringToNullUtil.emptyStringToNullUtil(queryPageRequest);
        BeanUtils.copyProperties(queryPageRequest, cmsPage);
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        //创建返回结果对象
        return ResponseResultUtil.getQueryResponseResult(all.getContent(), all.getTotalElements());
    }

    /**
     * 新增一条cms页面信息
     */
    @Override
    @Transactional
    public CmsPageResult add(CmsPage cmsPage) {
        //校验该页面信息是否存在
        CmsPage queryCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath
                (cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        CmsPage cmsPage1 = null;
        try {
            if (queryCmsPage == null){
                cmsPage1 = cmsPageRepository.save(cmsPage);
            }else {
                String pageId = queryCmsPage.getPageId();
                BeanUtils.copyProperties(cmsPage,queryCmsPage);
                queryCmsPage.setPageId(pageId);
                cmsPage1 = cmsPageRepository.save(queryCmsPage);
            }
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage1);
        }catch (Exception e){
            e.printStackTrace();
            return new CmsPageResult(CommonCode.FAIL,null);
        }
    }

    /**
     * 根据主键id查询cms页面信息
     */
    @Override
    public CmsPageResult get(String pageId) {
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (optionalCmsPage.isPresent()) {
            CmsPage cmsPage = optionalCmsPage.get();
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CommonCode.NODATA, null);
    }

    /**
     * 修改cms页面信息
     */
    @Override
    public ResponseResult update(CmsPage cmsPage) {
        if (cmsPage.getPageId() == null) {
            return new ResponseResult(CommonCode.FAIL);
        }
        try {
            EmptyStringToNullUtil.emptyStringToNullUtil(cmsPage);
            cmsPageRepository.save(cmsPage);
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    @Override
    @Transactional
    public ResponseResult delete(String pageId) {
        if (StringUtils.isEmpty(pageId))
            return ResponseResult.FAIL();
        try {
            cmsPageRepository.deleteById(pageId);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
    }

    /**
     * 静态化html页面
     */
    @Override
    @Transactional
    public GenerateHtmlResult getPageHtml(String pageId) {
        //获取页面的信息
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_NOPAGEDATA);
        }
        CmsPage cmsPage = optionalCmsPage.get();

        //获取模板所需的数据源
        Map map = getHtmlData(cmsPage);

        //获取模板文件
        Optional<CmsTemplate> optionalCmsTemplate = cmsTemplateRepository.findById(cmsPage.getTemplateId());
        if (!optionalCmsTemplate.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = optionalCmsTemplate.get();
        String templateString = getTemplateData(cmsPage,cmsTemplate);
        if (StringUtils.isEmpty(templateString)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //静态化
        String html = generateHtml(templateString, map);
        if (StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }

        //返回结果
        return new GenerateHtmlResult(CommonCode.SUCCESS,html);
    }

    /**
     * 保存静态化页面
     * */
    @Override
    public ResponseResult saveGenerateHtml(String pageId) {
        //获取静态化页面的内容
        String html = this.getPageHtml(pageId).getHtml();

        //获取页面的基本信息和模板信息
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(pageId);
        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(cmsPage.get().getTemplateId());

        //保存静态化页面到gridFS文件系统,并把静态页面文件id添加到cms页面信息中
        boolean boo = this.saveHtmlDataFile(html, cmsTemplate.get().getTemplateName(), cmsPage.get());

        //返回保存结果
        return boo ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    /**
     * 获取静态文件数据
     * */
    @Override
    public String getStaticFileData(String pageId) {
        //获取页面的基本信息
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_NOPAGEDATA);
        }
        CmsPage cmsPage = optionalCmsPage.get();
        //判断静态文件是否存在
        if (StringUtils.isEmpty(cmsPage.getHtmlFileId())){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_STATICFILEIDISNULL);
        }
        //从gridFS中获取静态文件数据
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsPage.getHtmlFileId())));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            InputStream inputStream = gridFsResource.getInputStream();
            return IOUtils.toString(inputStream, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *发布页面
     * */
    @Override
    @Transactional
    public ResponseResult postPage(String pageId) {
        //校验
        if (StringUtils.isEmpty(pageId)){
            ExceptionCast.cast(CmsCode.CMS_COURSE_PARAMISNULL);
        }

        //根据pageId获取cms页面信息
        Optional<CmsPage> pageOptional = cmsPageRepository.findById(pageId);
        if (!pageOptional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_NOPAGEDATA);
        }
        CmsPage cmsPage = pageOptional.get();

        //根据templateId获取模板页面信息
        if (cmsPage.getTemplateId() == null){
            ExceptionCast.cast(CmsCode.CMS_COURSE_TEMPLATEIDISNULL);
        }
        Optional<CmsTemplate> templateOptional = cmsTemplateRepository.findById(cmsPage.getTemplateId());
        if (!templateOptional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEIERORR);
        }
        CmsTemplate cmsTemplate = templateOptional.get();

        //静态化
        GenerateHtmlResult pageHtml = getPageHtml(cmsPage.getPageId());
        String html = pageHtml.getHtml();

        //把静态化页面保存到gridFs中
        saveHtmlDataFile(html,cmsTemplate.getTemplateName(),cmsPage);

        //定义向mq发送的数据
        Map<String, String> map = new HashMap<>();
        map.put("pageId",cmsPage.getPageId());
        String msg = JSON.toJSONString(map);

        //通知rabbitmq页面静态化完成并保存到gridFS
        prosucer(msg);

        //返回结果
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取页面数据
     */
    private Map getHtmlData(CmsPage cmsPage) {
        if (StringUtils.isEmpty(cmsPage.getDataUrl())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        try{
            //页面管理的数据源
            ResponseEntity<Map> forEntity = restTemplate.getForEntity(cmsPage.getDataUrl(), Map.class);
            Map map = (Map) forEntity.getBody().get("cmsConfig");
            if (map == null) {
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
            }
            return map;
        }catch (Exception e){
            //课程管理的数据源
            ResponseEntity<CourseView> forEntity = restTemplate.getForEntity(cmsPage.getDataUrl(), CourseView.class);
            CourseView body = forEntity.getBody();
            String string = JSON.toJSONString(body);
            Map map = JSON.parseObject(string, Map.class);
            return map;
        }
    }

    /**
     * 获取模板内容
     */
    private String getTemplateData(CmsPage cmsPage,CmsTemplate cmsTemplate) {
        if (StringUtils.isEmpty(cmsPage.getTemplateId())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        if (StringUtils.isEmpty(cmsTemplate.getTemplateFileId())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            InputStream inputStream = gridFsResource.getInputStream();
            String templateString = IOUtils.toString(inputStream, "utf-8");
            return templateString;
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEIERORR);
        }
        return null;
    }

    /**
     * 静态化
     */
    private String generateHtml(String templateString, Map model) {
        try {
            //生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", templateString);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存静态html文件到GridFS中
     * */
    private boolean saveHtmlDataFile(String htmlString,String templateName,CmsPage cmsPage){
        boolean boo = false;
        try {
            //删除原有的静态数据文件
            if (StringUtils.isEmpty(cmsPage.getHtmlFileId())){
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(cmsPage.getHtmlFileId())));
            }
            InputStream inputStream = IOUtils.toInputStream(htmlString, "utf-8");
            ObjectId objectId = gridFsTemplate.store(inputStream, templateName);
            cmsPage.setHtmlFileId(objectId.toString());
            cmsPage.setPageType("0");
            cmsPageRepository.save(cmsPage);

            /*Optional<CmsTemplate> templateOptional = cmsTemplateRepository.findById(cmsPage.getTemplateId());
            if (templateOptional.isPresent()){
                CmsTemplate cmsTemplate = templateOptional.get();
                cmsTemplate.setTemplateFileId(objectId.toString());
                cmsTemplateRepository.save(cmsTemplate);
            }*/
            boo = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boo;
    }

    /**
     * 执行页面静态化
     * */
    @Override
    public String initGenerateHtml(String pageId){
        //获取页面的基本信息
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        if (!optionalCmsPage.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_NOPAGEDATA);
        }
        CmsPage cmsPage = optionalCmsPage.get();

        //获取模板信息
        Optional<CmsTemplate> cmsTemplateOptional = cmsTemplateRepository.findById(cmsPage.getTemplateId());
        if (!cmsTemplateOptional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = cmsTemplateOptional.get();
        if (cmsTemplate.getTemplateFileId() == null){
            ExceptionCast.cast(CmsCode.CMS_COURSE_TEMPLATEIDISNULL);
        }

        //获取模板所需的数据源
        Map map = this.getHtmlData(cmsPage);

        //获取模板文件
        String templateDataString = this.getTemplateData(cmsPage, cmsTemplate);

        //静态化
        String generateHtml = this.generateHtml(templateDataString, map);

        //返回静态数据
        return generateHtml;

    }

    /**
     * 一键发布课程页面信息
     * */
    @Override
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //String pageName = UUID.randomUUID().toString().replace("-","") + ".html";
        //校验cmsPage页面信息
        if (StringUtils.isEmpty(cmsPage.getPageWebPath()))
            ExceptionCast.cast(CmsCode.CMS_COURSE_PAGEPATHISERROR);
        //cmsPage.setPageName(pageName);

        //判断cms页面信息是否存在，若存在更新，不存保存页面信息
        CmsPage queryCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath
                (cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (queryCmsPage == null){
            cmsPage = cmsPageRepository.save(cmsPage);
        }else {
            String pageId = queryCmsPage.getPageId();
            BeanUtils.copyProperties(cmsPage,queryCmsPage);
            queryCmsPage.setPageId(pageId);
            cmsPage = cmsPageRepository.save(queryCmsPage);
        }

        //查询站点信息
        if (StringUtils.isEmpty(cmsPage.getSiteId()))
            ExceptionCast.cast(CmsCode.CMS_COURSE_SITEIDISNULL);
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(cmsPage.getSiteId());
        if (!cmsSiteOptional.isPresent())
            ExceptionCast.cast(CmsCode.CMS_COURSE_SITEDATAISNULL);
        CmsSite cmsSite = cmsSiteOptional.get();

        //校验站点路径
        if (StringUtils.isEmpty(cmsSite.getSiteDomain()))
            ExceptionCast.cast(CmsCode.CMS_COURSE_SITEPATHISERROR);

        //静态化页面
        GenerateHtmlResult generateHtmlResult = this.getPageHtml(cmsPage.getPageId());
        if (StringUtils.isEmpty(generateHtmlResult.getHtml()))
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);

        //把静态化数据保存到GridFS中
        boolean boo = this.saveHtmlDataFile(generateHtmlResult.getHtml(), "课程页面" + cmsPage.getPageName(), cmsPage);
        if (!boo){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }

        //定义向mq发送的数据
        Map<String, String> map = new HashMap<>();
        map.put("pageId",cmsPage.getPageId());
        String msg = JSON.toJSONString(map);

        //通知rabbitmq页面静态化完成并保存到gridFS
        prosucer(msg);

        //返回结果数据
        String pageUrl = cmsSite.getSiteDomain() + cmsSite.getSiteWebPath()
                + cmsPage.getPageWebPath() + cmsPage.getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }


    /**
     * 向rabbitmq发送消息
     * */
    private void prosucer(String msg){
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,rabbitmqConfig.routingKey,msg);
    }
}
