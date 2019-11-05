package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 杜承旭
 * @ClassNmae: RabbitmqConfig
 * @Description: TODO
 * @date 2019/10/22 16:26
 * @Version 1.0
 **/

@Component
public class RabbitmqConfig {

    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";

    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";

    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    @Value("${xuecheng.mq.routingkey}")
    public String routingKey;

    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE(){
        return new Queue(QUEUE_CMS_POSTPAGE);
    }

    @Bean
    public Binding  BINDING_QUEUE_INFORM_SMS
            (@Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange,@Qualifier(QUEUE_CMS_POSTPAGE)Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }


}
