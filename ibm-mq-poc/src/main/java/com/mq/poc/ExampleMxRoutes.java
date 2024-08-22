package com.mq.poc;

import com.ibm.mq.MQException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExampleMxRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
// Error handling for MQException
        onException(MQException.class)
                .handled(true)
                .log("Exception occurred, routing to backup queue")
                .to("wmq2:queue:DEV.QUEUE.1");

        // Main route for sending messages
        from("direct:startBusinessTransfer")
                .log("Sending number to queue")
                .to("wmq1:queue:DEV.QUEUE.1");

    }

}
