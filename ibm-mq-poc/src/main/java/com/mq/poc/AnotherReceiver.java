package com.mq.poc;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author sumit
 */
@Component
public class AnotherReceiver {

    @JmsListener(destination = "DEV.QUEUE.1",containerFactory = "jmsListenerQM1")
    public void receiveMessage(String message) {
        System.out.println("DEV.QUEUE.1 received ~" + message + "~ from QM1 CF");
    }
}
