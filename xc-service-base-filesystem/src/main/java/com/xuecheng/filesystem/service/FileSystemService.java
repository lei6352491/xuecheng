package com.xuecheng.filesystem.service;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @version 1.0
 **/

public interface FileSystemService {

    //上传文件及文件信息
    UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

}
