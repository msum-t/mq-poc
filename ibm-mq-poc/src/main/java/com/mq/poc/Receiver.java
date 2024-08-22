package com.mq.poc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sumit
 */
@Component
public class Receiver {

    @JmsListener(destination = "DEV.QUEUE.1",containerFactory = "jmsListenerQM2")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.1 received ~" + message + "~ from QM2 CF");
    }
}


