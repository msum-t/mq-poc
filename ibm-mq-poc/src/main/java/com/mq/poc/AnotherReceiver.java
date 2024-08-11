package com.mq.poc;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author bjzquan
 */
@Component
public class AnotherReceiver {

    //@JmsListener(destination = "DEV.QUEUE.2",containerFactory = "jmsListenerContainerFactoryQM1")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.2 received ~" + message + "~");
    }
}
