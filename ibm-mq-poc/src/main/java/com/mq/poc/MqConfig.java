package com.mq.poc;

import com.ibm.mq.MQException;
import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MqConfig {

   @Bean
    public ConnectionFactory qm1ConnectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setQueueManager("QM2");
        factory.setHostName("localhost");
        factory.setPort(1415);
        factory.setChannel("DEV.ADMIN.SVRCONN");
        factory.setStringProperty(WMQConstants.USERID,"admin");
        factory.setStringProperty(WMQConstants.PASSWORD,"passw0rd");
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }


    @Bean("jmsTemplateQM1")
    public JmsTemplate jmsTemplateQM1() throws Exception {
        return new JmsTemplate(qm1ConnectionFactory());
    }

    @Bean("jmsListenerQM1")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws Exception {
        var factory=new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(qm1ConnectionFactory());
       return factory ;
    }

}
