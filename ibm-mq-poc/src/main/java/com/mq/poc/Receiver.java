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

    @JmsListener(destination = "DEV.QUEUE.2",containerFactory = "jmsListenerQM1")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.2 received ~" + message + "~ from custom CF");
    }
}


