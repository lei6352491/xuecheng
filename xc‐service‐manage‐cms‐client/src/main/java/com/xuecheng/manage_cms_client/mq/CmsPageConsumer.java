package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.xuecheng.manage_cms_client.service.CmsPageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author 杜承旭
 * @ClassNmae: CmsPageConsumer
 * @Description: TODO
 * @date 2019/10/23 13:57
 * @Version 1.0
 **/

@Component
public class CmsPageConsumer {

    @Autowired
    private CmsPageService cmsPageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"} )
    public void saveHtmlToServicePath(Message message, Channel channel){
        byte[] body = message.getBody();
        String msg = null;
        try {
            msg = new String(body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map map = JSON.parseObject(msg, Map.class);
        Object pageIdObject = map.get("pageId");
        cmsPageService.saveHtmlToServicePath(pageIdObject.toString());
    }

}
