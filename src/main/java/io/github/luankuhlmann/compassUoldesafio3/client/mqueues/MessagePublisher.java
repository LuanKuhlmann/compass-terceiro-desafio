package io.github.luankuhlmann.compassUoldesafio3.client.mqueues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String queue, final String messageStr) {
        jmsTemplate.send(queue, session -> session.createObjectMessage(messageStr));
    }
}
