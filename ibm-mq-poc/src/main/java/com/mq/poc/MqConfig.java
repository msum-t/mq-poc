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

    @ConfigurationProperties(prefix = "ibm.mq.qm1")
    @Bean
    public MQConfigurationProperties mqConfigurationPropertieQM1() {
        return new MQConfigurationProperties();
    }

    @ConfigurationProperties(prefix = "ibm.mq.qm2")
    @Bean
    public MQConfigurationProperties mqConfigurationPropertieQM2() {
        return new MQConfigurationProperties();
    }

   @Bean
    public ConnectionFactory qm1ConnectionFactory() throws JMSException {

        var props = mqConfigurationPropertieQM1();
       System.out.println(props.getQueueManager());
           MQConnectionFactory factory = new MQConnectionFactory();
           factory.setQueueManager(props.getQueueManager());
           factory.setConnectionNameList(props.getConnName());
           factory.setChannel(props.getChannel());
           factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }

    @Bean
    public ConnectionFactory qm2ConnectionFactory() throws Exception {
        var props = mqConfigurationPropertieQM2();
        System.out.println(props.getQueueManager());
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setQueueManager(props.getQueueManager());
        factory.setConnectionNameList(props.getConnName());
        factory.setChannel(props.getChannel());
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }


    @Bean("jmsTemplateQM1")
    public JmsTemplate jmsTemplateQM1() throws Exception {
        return new JmsTemplate(qm1ConnectionFactory());
    }

    @Bean("jmsTemplateQM2")
    public JmsTemplate jmsTemplateQM2() throws Exception {
        return new JmsTemplate(qm2ConnectionFactory());
    }

    @Bean("jmsListenerContainerFactoryQM1")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryQM1() throws Exception {
        var factory=new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(qm1ConnectionFactory());
        return factory;
    }
    @Bean("jmsListenerContainerFactoryQM2")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryQM2() throws Exception {
        var factory=new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(qm2ConnectionFactory());
        return factory;
    }
}
