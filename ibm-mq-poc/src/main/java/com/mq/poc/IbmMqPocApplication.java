package com.mq.poc;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.annotation.PostConstruct;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJms
@RestController
public class IbmMqPocApplication {

	@Autowired
	@Qualifier("jmsTemplateQM1")
	private JmsTemplate jmsTemplateQM1;

	@Autowired
	private JmsTemplate jmsTemplate;
	public static void main(String[] args) {
		SpringApplication.run(IbmMqPocApplication.class, args);
	}

	//@GetMapping("/send")
	@PostConstruct
	public void sendMsg(){
		jmsTemplate.convertAndSend("DEV.QUEUE.1","Hi from queue 1");
		System.out.println("sent");
		jmsTemplateQM1.convertAndSend("DEV.QUEUE.2","Hi from queue 2");
		System.out.println("sent");
		//return "msg sent";

	}
}



