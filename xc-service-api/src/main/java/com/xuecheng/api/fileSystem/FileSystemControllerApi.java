package com.xuecheng.api.fileSystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杜承旭
 * @ClassNmae: FileSystemControllerApi
 * @Description: TODO
 * @date 2019/10/26 15:07
 * @Version 1.0
 **/
public interface FileSystemControllerApi {

    @ApiOperation(value = "文件上传")
    UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

}
