package com.mq.poc;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.jms.JmsConstants;
import com.ibm.msg.client.jakarta.wmq.common.CommonConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import lombok.SneakyThrows;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MqConfig {

    @Bean
    public ConnectionFactory qm1ConnectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setQueueManager("QM1");
        factory.setConnectionNameList("localhost(1414)");
        factory.setChannel("DEV.ADMIN.SVRCONN");
        factory.setStringProperty(JmsConstants.USERID, "admin");
        factory.setStringProperty(JmsConstants.PASSWORD, "passw0rd");
        factory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
        return factory;
    }

    @Bean
    public ConnectionFactory qm2ConnectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setQueueManager("QM2");
        factory.setConnectionNameList("localhost(1415)");
        factory.setChannel("DEV.ADMIN.SVRCONN");
        factory.setStringProperty(JmsConstants.USERID, "admin");
        factory.setStringProperty(JmsConstants.PASSWORD, "passw0rd");
        factory.setTransportType(CommonConstants.WMQ_CM_CLIENT);
        return factory;
    }


    @Bean("jmsTemplateQM1")
    public JmsTemplate jmsTemplateQM1() throws Exception {
        return new JmsTemplate(qm1ConnectionFactory());
    }

    @Bean("jmsListenerQM1")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws Exception {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(qm1ConnectionFactory());
        return factory;
    }

    @Bean("jmsTemplateQM2")
    public JmsTemplate jmsTemplateQM2() throws Exception {
        return new JmsTemplate(qm2ConnectionFactory());
    }

    @Bean("jmsListenerQM2")
    public DefaultJmsListenerContainerFactory jmsListenerQM2() throws Exception {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(qm2ConnectionFactory());
        return factory;
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @SneakyThrows
            @Override
            public void beforeApplicationStart(CamelContext context) {
                context.addComponent("wmq1", JmsComponent.jmsComponentAutoAcknowledge(qm1ConnectionFactory()));
                context.addComponent("wmq2", JmsComponent.jmsComponentAutoAcknowledge(qm2ConnectionFactory()));

            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                // Do nothing
            }
        };
    }

}


